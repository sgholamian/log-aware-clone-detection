//,temp,FileSystemTimelineWriter.java,1007,1017,temp,KMSACLs.java,287,302
//,3
public class xxx {
  private boolean checkKeyAccess(String keyName, UserGroupInformation ugi,
      KeyOpType opType) {
    Map<KeyOpType, AccessControlList> keyAcl = keyAcls.get(keyName);
    if (keyAcl == null) {
      // If No key acl defined for this key, check to see if
      // there are key defaults configured for this operation
      LOG.debug("Key: {} has no ACLs defined, using defaults.", keyName);
      keyAcl = defaultKeyAcls;
    }
    boolean access = checkKeyAccess(keyAcl, ugi, opType);
    if (LOG.isDebugEnabled()) {
      LOG.debug("User: [{}], OpType: {}, KeyName: {} Result: {}",
          ugi.getShortUserName(), opType.toString(), keyName, access);
    }
    return access;
  }

};