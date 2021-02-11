//,temp,LdapManagerImpl.java,270,282,temp,LdapManagerImpl.java,224,239
//,3
public class xxx {
    @Override
    public List<LdapUser> getUsersInGroup(String groupName, Long domainId) throws NoLdapUserMatchingQueryException {
        LdapContext context = null;
        try {
            context = _ldapContextFactory.createBindContext(domainId);
            return _ldapUserManagerFactory.getInstance(_ldapConfiguration.getLdapProvider(domainId)).getUsersInGroup(groupName, context, domainId);
        } catch (NamingException | IOException e) {
            s_logger.debug("ldap NamingException: ",e);
            throw new NoLdapUserMatchingQueryException("groupName=" + groupName);
        } finally {
            closeContext(context);
        }
    }

};