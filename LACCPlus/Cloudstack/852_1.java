//,temp,LdapManagerImpl.java,298,312,temp,LdapManagerImpl.java,224,239
//,3
public class xxx {
    @Override
    public List<LdapUser> searchUsers(final String username) throws NoLdapUserMatchingQueryException {
        LdapContext context = null;
        try {
            // TODO search users per domain (only?)
            context = _ldapContextFactory.createBindContext(null);
            final String escapedUsername = LdapUtils.escapeLDAPSearchFilter(username);
            return _ldapUserManagerFactory.getInstance(_ldapConfiguration.getLdapProvider(null)).getUsers("*" + escapedUsername + "*", context, null);
        } catch (NamingException | IOException e) {
            s_logger.debug("ldap Exception: ",e);
            throw new NoLdapUserMatchingQueryException(username);
        } finally {
            closeContext(context);
        }
    }

};