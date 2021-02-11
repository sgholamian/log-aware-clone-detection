//,temp,sample_1024.java,2,18,temp,sample_3104.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (LOG.isDebugEnabled()) {
}
String user = callerUGI.getShortUserName();
if (!areACLsEnabled()) {
return true;
}
AccessControlList applicationACL = DEFAULT_YARN_APP_ACL;
Map<ApplicationAccessType, AccessControlList> acls = this.applicationACLS .get(applicationId);
if (acls == null) {
if (LOG.isDebugEnabled()) {


log.info("acl not found for application owned by using default");
}
}
}

};