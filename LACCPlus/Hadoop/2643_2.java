//,temp,HistoryServerFileSystemStateStoreService.java,201,211,temp,HistoryServerFileSystemStateStoreService.java,167,174
//,3
public class xxx {
  @Override
  public void removeToken(MRDelegationTokenIdentifier tokenId)
      throws IOException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Removing token " + tokenId.getSequenceNumber());
    }
    deleteFile(getTokenPath(tokenId));
  }

};