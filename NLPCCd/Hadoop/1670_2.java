//,temp,sample_6525.java,2,9,temp,sample_4210.java,2,9
//,3
public class xxx {
private boolean checkKeyAccess(String keyName, UserGroupInformation ugi, KeyOpType opType) {
Map<KeyOpType, AccessControlList> keyAcl = keyAcls.get(keyName);
if (keyAcl == null) {


log.info("key has no acls defined using defaults");
}
}

};