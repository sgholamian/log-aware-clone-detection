//,temp,LinkAccountToLdapCmd.java,71,107,temp,LinkDomainToLdapCmd.java,95,130
//,3
public class xxx {
    @Override
    public void execute() throws ServerApiException {
        try {
            LinkDomainToLdapResponse response = _ldapManager.linkDomainToLdap(this);
            if(admin!=null) {
                LdapUser ldapUser = null;
                try {
                    ldapUser = _ldapManager.getUser(admin, type, getLdapDomain(), domainId);
                } catch (NoLdapUserMatchingQueryException e) {
                    s_logger.debug("no ldap user matching username " + admin + " in the given group/ou", e);
                }
                if (ldapUser != null && !ldapUser.isDisabled()) {
                    Account account = _accountService.getActiveAccountByName(admin, domainId);
                    if (account == null) {
                        try {
                            UserAccount userAccount = _accountService.createUserAccount(admin, "", ldapUser.getFirstname(), ldapUser.getLastname(), ldapUser.getEmail(), null,
                                    admin, Account.ACCOUNT_TYPE_DOMAIN_ADMIN, RoleType.DomainAdmin.getId(), domainId, null, null, UUID.randomUUID().toString(), UUID.randomUUID().toString(), User.Source.LDAP);
                            response.setAdminId(String.valueOf(userAccount.getAccountId()));
                            s_logger.info("created an account with name " + admin + " in the given domain " + domainId);
                        } catch (Exception e) {
                            s_logger.info("an exception occurred while creating account with name " + admin +" in domain " + domainId, e);
                        }
                    } else {
                        s_logger.debug("an account with name " + admin + " already exists in the domain " + domainId);
                    }
                } else {
                    s_logger.debug("ldap user with username "+admin+" is disabled in the given group/ou");
                }
            }
            response.setObjectName("LinkDomainToLdap");
            response.setResponseName(getCommandName());
            setResponseObject(response);
        } catch (final InvalidParameterValueException e) {
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, e.toString());
        }
    }

};