//,temp,LeveldbRMStateStore.java,716,735,temp,LeveldbRMStateStore.java,634,654
//,3
public class xxx {
  @Override
  protected void storeRMDTMasterKeyState(DelegationKey masterKey)
      throws IOException {
    String dbKey = getRMDTMasterKeyNodeKey(masterKey);
    if (LOG.isDebugEnabled()) {
      LOG.debug("Storing token master key to " + dbKey);
    }
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(os);
    try {
      masterKey.write(out);
    } finally {
      out.close();
    }
    try {
      db.put(bytes(dbKey), os.toByteArray());
    } catch (DBException e) {
      throw new IOException(e);
    }
  }

};