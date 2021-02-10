//,temp,ApplicationHistoryServer.java,145,165,temp,JobHistoryServer.java,219,238
//,3
public class xxx {
  static JobHistoryServer launchJobHistoryServer(String[] args) {
    Thread.
        setDefaultUncaughtExceptionHandler(new YarnUncaughtExceptionHandler());
    StringUtils.startupShutdownMessage(JobHistoryServer.class, args, LOG);
    JobHistoryServer jobHistoryServer = null;
    try {
      jobHistoryServer = new JobHistoryServer();
      ShutdownHookManager.get().addShutdownHook(
          new CompositeServiceShutdownHook(jobHistoryServer),
          SHUTDOWN_HOOK_PRIORITY);
      YarnConfiguration conf = new YarnConfiguration(new JobConf());
      new GenericOptionsParser(conf, args);
      jobHistoryServer.init(conf);
      jobHistoryServer.start();
    } catch (Throwable t) {
      LOG.fatal("Error starting JobHistoryServer", t);
      ExitUtil.terminate(-1, "Error starting JobHistoryServer");
    }
    return jobHistoryServer;
  }

};