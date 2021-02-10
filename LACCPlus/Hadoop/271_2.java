//,temp,HistoryServerFileSystemStateStoreService.java,412,428,temp,HistoryServerFileSystemStateStoreService.java,396,410
//,3
public class xxx {
  private int loadKeys(HistoryServerState state) throws IOException {
    FileStatus[] stats = fs.listStatus(tokenKeysStatePath);
    int numKeys = 0;
    for (FileStatus stat : stats) {
      String name = stat.getPath().getName();
      if (name.startsWith(TOKEN_MASTER_KEY_FILE_PREFIX)) {
        loadTokenMasterKey(state, stat.getPath(), stat.getLen());
        ++numKeys;
      } else {
        LOG.warn("Skipping unexpected file in history server token state: "
            + stat.getPath());
      }
    }
    return numKeys;
  }

};