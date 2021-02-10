//,temp,ApplicationHistoryServer.java,164,184,temp,SharedCacheManager.java,148,163
//,3
public class xxx {
  static ApplicationHistoryServer launchAppHistoryServer(String[] args) {
    Thread
      .setDefaultUncaughtExceptionHandler(new YarnUncaughtExceptionHandler());
    StringUtils.startupShutdownMessage(ApplicationHistoryServer.class, args,
      LOG);
    ApplicationHistoryServer appHistoryServer = null;
    try {
      appHistoryServer = new ApplicationHistoryServer();
      ShutdownHookManager.get().addShutdownHook(
        new CompositeServiceShutdownHook(appHistoryServer),
        SHUTDOWN_HOOK_PRIORITY);
      YarnConfiguration conf = new YarnConfiguration();
      new GenericOptionsParser(conf, args);
      appHistoryServer.init(conf);
      appHistoryServer.start();
    } catch (Throwable t) {
      LOG.error("Error starting ApplicationHistoryServer", t);
      ExitUtil.terminate(-1, "Error starting ApplicationHistoryServer");
    }
    return appHistoryServer;
  }

};