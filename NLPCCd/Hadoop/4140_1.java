//,temp,sample_6838.java,2,15,temp,sample_337.java,2,13
//,3
public class xxx {
public boolean addDigestACL(ACL acl) {
if (secureRegistry) {
if (LOG.isDebugEnabled()) {
}
digestACLs.add(acl);
return true;
} else {
if (LOG.isDebugEnabled()) {


log.info("ignoring added acl registry is insecure");
}
}
}

};