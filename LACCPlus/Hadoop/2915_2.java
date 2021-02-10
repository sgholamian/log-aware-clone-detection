//,temp,JobHistoryServer.java,214,233,temp,TimelineReaderServer.java,221,240
//,3
public class xxx {
  static TimelineReaderServer startTimelineReaderServer(String[] args,
      Configuration conf) {
    Thread.setDefaultUncaughtExceptionHandler(
        new YarnUncaughtExceptionHandler());
    StringUtils.startupShutdownMessage(TimelineReaderServer.class,
        args, LOG);
    TimelineReaderServer timelineReaderServer = null;
    try {
      timelineReaderServer = new TimelineReaderServer();
      ShutdownHookManager.get().addShutdownHook(
          new CompositeServiceShutdownHook(timelineReaderServer),
          SHUTDOWN_HOOK_PRIORITY);
      timelineReaderServer.init(conf);
      timelineReaderServer.start();
    } catch (Throwable t) {
      LOG.error("Error starting TimelineReaderWebServer", t);
      ExitUtil.terminate(-1, "Error starting TimelineReaderWebServer");
    }
    return timelineReaderServer;
  }

};