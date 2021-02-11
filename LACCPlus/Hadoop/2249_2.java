//,temp,ApplicationHistoryServer.java,164,184,temp,PerNodeTimelineCollectorsAuxService.java,230,253
//,3
public class xxx {
  @VisibleForTesting
  public static PerNodeTimelineCollectorsAuxService
      launchServer(String[] args, NodeTimelineCollectorManager collectorManager,
      Configuration conf) {
    Thread
      .setDefaultUncaughtExceptionHandler(new YarnUncaughtExceptionHandler());
    StringUtils.startupShutdownMessage(
        PerNodeTimelineCollectorsAuxService.class, args, LOG);
    PerNodeTimelineCollectorsAuxService auxService = null;
    try {
      auxService = collectorManager == null ?
          new PerNodeTimelineCollectorsAuxService(
              new NodeTimelineCollectorManager(false)) :
          new PerNodeTimelineCollectorsAuxService(collectorManager);
      ShutdownHookManager.get().addShutdownHook(new ShutdownHook(auxService),
          SHUTDOWN_HOOK_PRIORITY);
      auxService.init(conf);
      auxService.start();
    } catch (Throwable t) {
      LOG.error("Error starting PerNodeTimelineCollectorServer", t);
      ExitUtil.terminate(-1, "Error starting PerNodeTimelineCollectorServer");
    }
    return auxService;
  }

};