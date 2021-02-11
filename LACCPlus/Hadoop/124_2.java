//,temp,HistoryServerLeveldbStateStoreService.java,259,282,temp,HistoryServerLeveldbStateStoreService.java,212,236
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
      IOUtils.cleanup(LOG, dataStream);
    }

    String dbKey = getTokenDatabaseKey(tokenId);
    try {
      db.put(bytes(dbKey), memStream.toByteArray());
    } catch (DBException e) {
      throw new IOException(e);
    }
  }

};