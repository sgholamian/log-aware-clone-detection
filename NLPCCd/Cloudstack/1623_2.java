//,temp,sample_2734.java,2,12,temp,sample_2733.java,2,12
//,3
public class xxx {
public List<LdapUser> getUsers() throws NoLdapUserMatchingQueryException {
LdapContext context = null;
try {
context = _ldapContextFactory.createBindContext();
return _ldapUserManagerFactory.getInstance(_ldapConfiguration.getLdapProvider()).getUsers(context);
} catch (NamingException | IOException e) {


log.info("ldap exception");
}
}

};