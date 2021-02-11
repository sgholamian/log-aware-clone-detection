//,temp,sample_2085.java,2,17,temp,sample_3105.java,2,18
//,3
public class xxx {
public void dummy_method(){
AccessControlList applicationACL = DEFAULT_YARN_APP_ACL;
Map<ApplicationAccessType, AccessControlList> acls = this.applicationACLS .get(applicationId);
if (acls == null) {
if (LOG.isDebugEnabled()) {
}
} else {
AccessControlList applicationACLInMap = acls.get(applicationAccessType);
if (applicationACLInMap != null) {
applicationACL = applicationACLInMap;
} else if (LOG.isDebugEnabled()) {


log.info("acl not found for access type for application owned by using default");
}
}
}

};