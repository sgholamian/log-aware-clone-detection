//,temp,sample_4714.java,2,14,temp,sample_4713.java,2,14
//,2
public class xxx {
public void apply(DirSearch ldap, String user) throws AuthenticationException {
List<String> groupDns = new ArrayList<>();
for (String groupId : groupFilter) {
try {
String groupDn = ldap.findGroupDn(groupId);
groupDns.add(groupDn);
} catch (NamingException e) {


log.info("cannot find dn for group");
}
}
}

};