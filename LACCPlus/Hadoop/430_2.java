//,temp,LeveldbRMStateStore.java,150,175,temp,HistoryServerLeveldbStateStoreService.java,73,99
//,3
public class xxx {
  @Override
  protected void startStorage() throws IOException {
    Path storeRoot = createStorageDir(getConfig());
    Options options = new Options();
    options.createIfMissing(false);
    options.logger(new LeveldbLogger());
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
    checkVersion();
  }

};