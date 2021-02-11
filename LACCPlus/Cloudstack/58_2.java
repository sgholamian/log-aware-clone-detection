//,temp,LdapManagerImpl.java,241,254,temp,LdapManagerImpl.java,224,239
//,3
public class xxx {
    @Override
    public LdapUser getUser(final String username, Long domainId) throws NoLdapUserMatchingQueryException {
        LdapContext context = null;
        try {
            context = _ldapContextFactory.createBindContext(domainId);

            final String escapedUsername = LdapUtils.escapeLDAPSearchFilter(username);
            return _ldapUserManagerFactory.getInstance(_ldapConfiguration.getLdapProvider(null)).getUser(escapedUsername, context, domainId);

        } catch (NamingException | IOException e) {
            s_logger.debug("ldap Exception: ",e);
            throw new NoLdapUserMatchingQueryException("No Ldap User found for username: "+username);
        } finally {
            closeContext(context);
        }
    }

};