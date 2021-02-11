//,temp,RollingLevelDB.java,324,342,temp,AbstractLivelinessMonitor.java,132,160
//,3
public class xxx {
  private synchronized void scheduleOldDBsForEviction() {
    // keep at least time to live amount of data
    long evictionThreshold = computeCurrentCheckMillis(currentTimeMillis()
        - getTimeToLive());

    LOG.info("Scheduling " + getName() + " DBs older than "
        + fdf.format(evictionThreshold) + " for eviction");
    Iterator<Entry<Long, DB>> iterator = rollingdbs.entrySet().iterator();
    while (iterator.hasNext()) {
      Entry<Long, DB> entry = iterator.next();
      // parse this in gmt time
      if (entry.getKey() < evictionThreshold) {
        LOG.info("Scheduling " + getName() + " eviction for "
            + fdf.format(entry.getKey()));
        iterator.remove();
        rollingdbsToEvict.put(entry.getKey(), entry.getValue());
      }
    }
  }

};