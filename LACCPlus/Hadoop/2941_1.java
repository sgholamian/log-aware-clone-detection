//,temp,CleanerTask.java,70,90,temp,CleanerService.java,175,192
//,3
public class xxx {
  public static CleanerTask create(Configuration conf, SCMStore store,
      CleanerMetrics metrics, Lock cleanerTaskLock) {
    try {
      // get the root directory for the shared cache
      String location =
          conf.get(YarnConfiguration.SHARED_CACHE_ROOT,
              YarnConfiguration.DEFAULT_SHARED_CACHE_ROOT);

      long sleepTime =
          conf.getLong(YarnConfiguration.SCM_CLEANER_RESOURCE_SLEEP_MS,
              YarnConfiguration.DEFAULT_SCM_CLEANER_RESOURCE_SLEEP_MS);
      int nestedLevel = SharedCacheUtil.getCacheDepth(conf);
      FileSystem fs = FileSystem.get(conf);

      return new CleanerTask(location, sleepTime, nestedLevel, fs, store,
          metrics, cleanerTaskLock);
    } catch (IOException e) {
      LOG.error("Unable to obtain the filesystem for the cleaner service", e);
      throw new ExceptionInInitializerError(e);
    }
  }

};