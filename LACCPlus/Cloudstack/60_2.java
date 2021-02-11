//,temp,LdapManagerImpl.java,270,282,temp,LdapManagerImpl.java,256,268
//,3
public class xxx {
    @Override
    public List<LdapUser> getUsers(Long domainId) throws NoLdapUserMatchingQueryException {
        LdapContext context = null;
        try {
            context = _ldapContextFactory.createBindContext(domainId);
            return _ldapUserManagerFactory.getInstance(_ldapConfiguration.getLdapProvider(domainId)).getUsers(context, domainId);
        } catch (NamingException | IOException e) {
            s_logger.debug("ldap Exception: ",e);
            throw new NoLdapUserMatchingQueryException("*");
        } finally {
            closeContext(context);
        }
    }

};