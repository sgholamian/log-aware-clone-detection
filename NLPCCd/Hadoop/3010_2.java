//,temp,sample_7520.java,2,11,temp,sample_6842.java,2,11
//,3
public class xxx {
public ACL createACLfromUsername(String username, int perms) {
if (usesRealm && !username.contains("@")) {
username = username + "@" + kerberosRealm;
if (LOG.isDebugEnabled()) {


log.info("appending kerberos realm to make");
}
}
}

};