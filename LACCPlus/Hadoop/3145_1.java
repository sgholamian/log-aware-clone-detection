//,temp,LeveldbRMStateStore.java,162,186,temp,HistoryServerLeveldbStateStoreService.java,72,97
//,3
public class xxx {
  protected DB openDatabase() throws Exception {
    Path storeRoot = createStorageDir();
    Options options = new Options();
    options.createIfMissing(false);
    LOG.info("Using state database at " + storeRoot + " for recovery");
    File dbfile = new File(storeRoot.toString());
    try {
      db = JniDBFactory.factory.open(dbfile, options);
    } catch (NativeDB.DBException e) {
      if (e.isNotFound() || e.getMessage().contains(" does not exist ")) {
        LOG.info("Creating state database at " + dbfile);
        options.createIfMissing(true);
        try {
          db = JniDBFactory.factory.open(dbfile, options);
          // store version
          storeVersion();
        } catch (DBException dbErr) {
          throw new IOException(dbErr.getMessage(), dbErr);
        }
      } else {
        throw e;
      }
    }
    return db;
  }

};