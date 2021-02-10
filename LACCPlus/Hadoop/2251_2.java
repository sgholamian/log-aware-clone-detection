//,temp,ApplicationHistoryServer.java,164,184,temp,DFSRouter.java,55,75
//,3
public class xxx {
  public static void main(String[] argv) {
    if (DFSUtil.parseHelpArgument(argv, USAGE, System.out, true)) {
      System.exit(0);
    }

    try {
      StringUtils.startupShutdownMessage(Router.class, argv, LOG);

      Router router = new Router();

      ShutdownHookManager.get().addShutdownHook(
          new CompositeServiceShutdownHook(router), SHUTDOWN_HOOK_PRIORITY);

      Configuration conf = new HdfsConfiguration();
      router.init(conf);
      router.start();
    } catch (Throwable e) {
      LOG.error("Failed to start router", e);
      terminate(1, e);
    }
  }

};