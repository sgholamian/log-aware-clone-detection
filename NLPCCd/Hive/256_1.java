//,temp,sample_4709.java,2,18,temp,sample_4710.java,2,18
//,2
public class xxx {
public void dummy_method(){
List<String> memberOf = null;
try {
String userDn = ldap.findUserDn(user);
memberOf = ldap.findGroupsForUser(userDn);
} catch (NamingException e) {
throw new AuthenticationException("LDAP Authentication failed for user", e);
}
for (String groupDn : memberOf) {
String shortName = LdapUtils.getShortName(groupDn);
if (groupFilter.contains(shortName)) {


log.info("groupmembershipkeyfilter passes user is a member of group");
}
}
}

};