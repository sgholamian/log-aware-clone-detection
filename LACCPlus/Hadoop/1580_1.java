//,temp,LeveldbRMStateStore.java,656,686,temp,NMLeveldbStateStoreService.java,564,585
//,3
public class xxx {
  private void storeOrUpdateRMDT(RMDelegationTokenIdentifier tokenId,
      Long renewDate, boolean isUpdate) throws IOException {
    String tokenKey = getRMDTTokenNodeKey(tokenId);
    RMDelegationTokenIdentifierData tokenData =
        new RMDelegationTokenIdentifierData(tokenId, renewDate);
    if (LOG.isDebugEnabled()) {
      LOG.debug("Storing token to " + tokenKey);
    }
    try {
      WriteBatch batch = db.createWriteBatch();
      try {
        batch.put(bytes(tokenKey), tokenData.toByteArray());
        if(!isUpdate) {
          ByteArrayOutputStream bs = new ByteArrayOutputStream();
          try (DataOutputStream ds = new DataOutputStream(bs)) {
            ds.writeInt(tokenId.getSequenceNumber());
          }
          if (LOG.isDebugEnabled()) {
            LOG.debug("Storing " + tokenId.getSequenceNumber() + " to "
                + RM_DT_SEQUENCE_NUMBER_KEY);   
          }
          batch.put(bytes(RM_DT_SEQUENCE_NUMBER_KEY), bs.toByteArray());
        }
        db.write(batch);
      } finally {
        batch.close();
      }
    } catch (DBException e) {
      throw new IOException(e);
    }
  }

};