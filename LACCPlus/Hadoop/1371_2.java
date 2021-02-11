//,temp,LeveldbRMStateStore.java,604,624,temp,HistoryServerLeveldbStateStoreService.java,259,282
//,3
public class xxx {
  @Override
  public void storeTokenMasterKey(DelegationKey masterKey)
      throws IOException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Storing master key " + masterKey.getKeyId());
    }

    ByteArrayOutputStream memStream = new ByteArrayOutputStream();
    DataOutputStream dataStream = new DataOutputStream(memStream);
    try {
      masterKey.write(dataStream);
      dataStream.close();
      dataStream = null;
    } finally {
      IOUtils.cleanup(LOG, dataStream);
    }

    String dbKey = getTokenMasterKeyDatabaseKey(masterKey);
    try {
      db.put(bytes(dbKey), memStream.toByteArray());
    } catch (DBException e) {
      throw new IOException(e);
    }
  }

};