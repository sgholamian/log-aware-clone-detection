//,temp,LeveldbRMStateStore.java,407,439,temp,LeveldbRMStateStore.java,300,329
//,3
public class xxx {
  private int loadRMDTSecretManagerKeys(RMState state) throws IOException {
    int numKeys = 0;
    LeveldbIterator iter = null;
    try {
      iter = new LeveldbIterator(db);
      iter.seek(bytes(RM_DT_MASTER_KEY_KEY_PREFIX));
      while (iter.hasNext()) {
        Entry<byte[],byte[]> entry = iter.next();
        String key = asString(entry.getKey());
        if (!key.startsWith(RM_DT_MASTER_KEY_KEY_PREFIX)) {
          break;
        }
        DelegationKey masterKey = loadDelegationKey(entry.getValue());
        state.rmSecretManagerState.masterKeyState.add(masterKey);
        ++numKeys;
        if (LOG.isDebugEnabled()) {
          LOG.debug("Loaded RM delegation key from " + key
              + ": keyId=" + masterKey.getKeyId()
              + ", expirationDate=" + masterKey.getExpiryDate());
        }
      }
    } catch (DBException e) {
      throw new IOException(e);
    } finally {
      if (iter != null) {
        iter.close();
      }
    }
    return numKeys;
  }

};