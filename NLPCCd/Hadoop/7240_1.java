//,temp,sample_4213.java,2,13,temp,sample_7977.java,2,7
//,3
public class xxx {
private boolean checkKeyAccess(Map<KeyOpType, AccessControlList> keyAcl, UserGroupInformation ugi, KeyOpType opType) {
AccessControlList acl = keyAcl.get(opType);
if (acl == null) {
return false;
} else {
if (LOG.isDebugEnabled()) {


log.info("checking user for");
}
}
}

};