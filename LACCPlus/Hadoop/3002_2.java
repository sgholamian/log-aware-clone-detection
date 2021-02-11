//,temp,HistoryServerFileSystemStateStoreService.java,176,199,temp,HistoryServerLeveldbStateStoreService.java,210,234
//,3
public class xxx {
  @Override
  public void storeToken(MRDelegationTokenIdentifier tokenId, Long renewDate)
      throws IOException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Storing token " + tokenId.getSequenceNumber());
    }

    ByteArrayOutputStream memStream = new ByteArrayOutputStream();
    DataOutputStream dataStream = new DataOutputStream(memStream);
    try {
      tokenId.write(dataStream);
      dataStream.writeLong(renewDate);
      dataStream.close();
      dataStream = null;
    } finally {
      IOUtils.cleanupWithLogger(LOG, dataStream);
    }

    String dbKey = getTokenDatabaseKey(tokenId);
    try {
      db.put(bytes(dbKey), memStream.toByteArray());
    } catch (DBException e) {
      throw new IOException(e);
    }
  }

};