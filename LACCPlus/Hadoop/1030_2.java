//,temp,HistoryServerLeveldbStateStoreService.java,73,99,temp,LeveldbTimelineStateStore.java,87,130
//,3
public class xxx {
  @Override
  protected void startStorage() throws IOException {
    Options options = new Options();
    Path dbPath =
        new Path(
            getConfig().get(
                YarnConfiguration.TIMELINE_SERVICE_LEVELDB_STATE_STORE_PATH),
            DB_NAME);
    FileSystem localFS = null;
    try {
      localFS = FileSystem.getLocal(getConfig());
      if (!localFS.exists(dbPath)) {
        if (!localFS.mkdirs(dbPath)) {
          throw new IOException("Couldn't create directory for leveldb " +
              "timeline store " + dbPath);
        }
        localFS.setPermission(dbPath, LEVELDB_DIR_UMASK);
      }
    } finally {
      IOUtils.cleanup(LOG, localFS);
    }
    JniDBFactory factory = new JniDBFactory();
    try {
      options.createIfMissing(false);
      db = factory.open(new File(dbPath.toString()), options);
      LOG.info("Loading the existing database at th path: " + dbPath.toString());
      checkVersion();
    } catch (NativeDB.DBException e) {
      if (e.isNotFound() || e.getMessage().contains(" does not exist ")) {
        try {
          options.createIfMissing(true);
          db = factory.open(new File(dbPath.toString()), options);
          LOG.info("Creating a new database at th path: " + dbPath.toString());
          storeVersion(CURRENT_VERSION_INFO);
        } catch (DBException ex) {
          throw new IOException(ex);
        }
      } else {
        throw new IOException(e);
      }
    } catch (DBException e) {
      throw new IOException(e);
    }
  }

};