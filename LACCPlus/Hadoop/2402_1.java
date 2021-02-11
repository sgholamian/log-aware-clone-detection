//,temp,LeveldbRMStateStore.java,374,406,temp,HistoryServerLeveldbStateStoreService.java,117,149
//,3
public class xxx {
  private int loadRMDTSecretManagerTokens(RMState state) throws IOException {
    int numTokens = 0;
    LeveldbIterator iter = null;
    try {
      iter = new LeveldbIterator(db);
      iter.seek(bytes(RM_DT_TOKEN_KEY_PREFIX));
      while (iter.hasNext()) {
        Entry<byte[],byte[]> entry = iter.next();
        String key = asString(entry.getKey());
        if (!key.startsWith(RM_DT_TOKEN_KEY_PREFIX)) {
          break;
        }
        RMDelegationTokenIdentifierData tokenData = loadDelegationToken(
            entry.getValue());
        RMDelegationTokenIdentifier tokenId = tokenData.getTokenIdentifier();
        long renewDate = tokenData.getRenewDate();
        state.rmSecretManagerState.delegationTokenState.put(tokenId,
            renewDate);
        ++numTokens;
        if (LOG.isDebugEnabled()) {
          LOG.debug("Loaded RM delegation token from " + key
              + ": tokenId=" + tokenId + ", renewDate=" + renewDate);
        }
      }
    } catch (DBException e) {
      throw new IOException(e);
    } finally {
      if (iter != null) {
        iter.close();
      }
    }
    return numTokens;
  }

};