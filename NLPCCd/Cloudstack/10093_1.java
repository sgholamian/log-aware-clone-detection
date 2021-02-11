//,temp,sample_2735.java,2,13,temp,sample_2731.java,2,13
//,3
public class xxx {
public List<LdapUser> searchUsers(final String username) throws NoLdapUserMatchingQueryException {
LdapContext context = null;
try {
context = _ldapContextFactory.createBindContext();
final String escapedUsername = LdapUtils.escapeLDAPSearchFilter(username);
return _ldapUserManagerFactory.getInstance(_ldapConfiguration.getLdapProvider()).getUsers("*" + escapedUsername + "*", context);
} catch (NamingException | IOException e) {


log.info("ldap exception");
}
}

};