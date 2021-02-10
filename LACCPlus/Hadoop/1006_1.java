//,temp,RollingLevelDB.java,343,360,temp,AbstractLivelinessMonitor.java,112,139
//,3
public class xxx {
  public synchronized void evictOldDBs() {
    LOG.info("Evicting " + getName() + " DBs scheduled for eviction");
    Iterator<Entry<Long, DB>> iterator = rollingdbsToEvict.entrySet()
        .iterator();
    while (iterator.hasNext()) {
      Entry<Long, DB> entry = iterator.next();
      IOUtils.cleanup(LOG, entry.getValue());
      String dbName = fdf.format(entry.getKey());
      Path path = new Path(rollingDBPath, getName() + "." + dbName);
      try {
        LOG.info("Removing old db directory contents in " + path);
        lfs.delete(path, true);
      } catch (IOException ioe) {
        LOG.warn("Failed to evict old db " + path, ioe);
      }
      iterator.remove();
    }
  }

};