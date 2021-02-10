//,temp,JobHistoryServer.java,219,238,temp,SharedCacheManager.java,147,162
//,3
public class xxx {
  public static void main(String[] args) {
    Thread.setDefaultUncaughtExceptionHandler(new YarnUncaughtExceptionHandler());
    StringUtils.startupShutdownMessage(SharedCacheManager.class, args, LOG);
    try {
      Configuration conf = new YarnConfiguration();
      SharedCacheManager sharedCacheManager = new SharedCacheManager();
      ShutdownHookManager.get().addShutdownHook(
          new CompositeServiceShutdownHook(sharedCacheManager),
          SHUTDOWN_HOOK_PRIORITY);
      sharedCacheManager.init(conf);
      sharedCacheManager.start();
    } catch (Throwable t) {
      LOG.fatal("Error starting SharedCacheManager", t);
      System.exit(-1);
    }
  }

};