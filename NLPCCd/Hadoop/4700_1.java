//,temp,sample_4212.java,2,9,temp,sample_5481.java,2,9
//,3
public class xxx {
private boolean checkKeyAccess(Map<KeyOpType, AccessControlList> keyAcl, UserGroupInformation ugi, KeyOpType opType) {
AccessControlList acl = keyAcl.get(opType);
if (acl == null) {


log.info("no acl available for key denying access for");
}
}

};