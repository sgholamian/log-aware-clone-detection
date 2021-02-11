//,temp,HistoryServerLeveldbStateStoreService.java,109,117,temp,LeveldbTimelineStateStore.java,137,148
//,3
public class xxx {
  @Override
  public HistoryServerState loadState() throws IOException {
    HistoryServerState state = new HistoryServerState();
    int numKeys = loadTokenMasterKeys(state);
    LOG.info("Recovered " + numKeys + " token master keys");
    int numTokens = loadTokens(state);
    LOG.info("Recovered " + numTokens + " tokens");
    return state;
  }

};