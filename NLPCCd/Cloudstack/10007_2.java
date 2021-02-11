//,temp,sample_2731.java,2,13,temp,sample_2732.java,2,13
//,3
public class xxx {
public LdapUser getUser(final String username, final String type, final String name) throws NoLdapUserMatchingQueryException {
LdapContext context = null;
try {
context = _ldapContextFactory.createBindContext();
final String escapedUsername = LdapUtils.escapeLDAPSearchFilter(username);
return _ldapUserManagerFactory.getInstance(_ldapConfiguration.getLdapProvider()).getUser(escapedUsername, type, name, context);
} catch (NamingException | IOException e) {


log.info("ldap exception");
}
}

};