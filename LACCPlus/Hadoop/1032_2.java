//,temp,NMLeveldbStateStoreService.java,920,948,temp,ShuffleHandler.java,468,492
//,3
public class xxx {
  private void startStore(Path recoveryRoot) throws IOException {
    Options options = new Options();
    options.createIfMissing(false);
    options.logger(new LevelDBLogger());
    Path dbPath = new Path(recoveryRoot, STATE_DB_NAME);
    LOG.info("Using state database at " + dbPath + " for recovery");
    File dbfile = new File(dbPath.toString());
    try {
      stateDb = JniDBFactory.factory.open(dbfile, options);
    } catch (NativeDB.DBException e) {
      if (e.isNotFound() || e.getMessage().contains(" does not exist ")) {
        LOG.info("Creating state database at " + dbfile);
        options.createIfMissing(true);
        try {
          stateDb = JniDBFactory.factory.open(dbfile, options);
          storeVersion();
        } catch (DBException dbExc) {
          throw new IOException("Unable to create state store", dbExc);
        }
      } else {
        throw e;
      }
    }
    checkVersion();
  }

};