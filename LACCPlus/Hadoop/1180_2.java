//,temp,HistoryServerLeveldbStateStoreService.java,109,117,temp,LeveldbTimelineStateStore.java,137,148
//,3
public class xxx {
  @Override
  public TimelineServiceState loadState() throws IOException {
    LOG.info("Loading timeline service state from leveldb");
    TimelineServiceState state = new TimelineServiceState();
    int numKeys = loadTokenMasterKeys(state);
    int numTokens = loadTokens(state);
    loadLatestSequenceNumber(state);
    LOG.info("Loaded " + numKeys + " master keys and " + numTokens
        + " tokens from leveldb, and latest sequence number is "
        + state.getLatestSequenceNumber());
    return state;
  }

};