//,temp,HistoryServerFileSystemStateStoreService.java,412,428,temp,HistoryServerFileSystemStateStoreService.java,396,410
//,3
public class xxx {
  private int loadTokens(HistoryServerState state) throws IOException {
    FileStatus[] stats = fs.listStatus(tokenStatePath);
    int numTokens = 0;
    for (FileStatus stat : stats) {
      String name = stat.getPath().getName();
      if (name.startsWith(TOKEN_BUCKET_DIR_PREFIX)) {
        numTokens += loadTokensFromBucket(state, stat.getPath());
      } else if (name.equals(TOKEN_KEYS_DIR_NAME)) {
        // key loading is done elsewhere
        continue;
      } else {
        LOG.warn("Skipping unexpected file in history server token state: "
            + stat.getPath());
      }
    }
    return numTokens;
  }

};