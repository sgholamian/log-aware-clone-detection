//,temp,ZKRMStateStore.java,495,530,temp,ZKRMStateStore.java,450,479
//,3
public class xxx {
  private void loadRMDelegationKeyState(RMState rmState) throws Exception {
    List<String> childNodes =
        getChildren(dtMasterKeysRootPath);
    for (String childNodeName : childNodes) {
      String childNodePath = getNodePath(dtMasterKeysRootPath, childNodeName);
      byte[] childData = getData(childNodePath);

      if (childData == null) {
        LOG.warn("Content of " + childNodePath + " is broken.");
        continue;
      }

      ByteArrayInputStream is = new ByteArrayInputStream(childData);
      DataInputStream fsIn = new DataInputStream(is);

      try {
        if (childNodeName.startsWith(DELEGATION_KEY_PREFIX)) {
          DelegationKey key = new DelegationKey();
          key.readFields(fsIn);
          rmState.rmSecretManagerState.masterKeyState.add(key);
          if (LOG.isDebugEnabled()) {
            LOG.debug("Loaded delegation key: keyId=" + key.getKeyId()
                + ", expirationDate=" + key.getExpiryDate());
          }
        }
      } finally {
        is.close();
      }
    }
  }

};