//,temp,NodeManager.java,547,567,temp,SharedCacheManager.java,147,162
//,3
public class xxx {
  private void initAndStartNodeManager(Configuration conf, boolean hasToReboot) {
    try {

      // Remove the old hook if we are rebooting.
      if (hasToReboot && null != nodeManagerShutdownHook) {
        ShutdownHookManager.get().removeShutdownHook(nodeManagerShutdownHook);
      }

      nodeManagerShutdownHook = new CompositeServiceShutdownHook(this);
      ShutdownHookManager.get().addShutdownHook(nodeManagerShutdownHook,
                                                SHUTDOWN_HOOK_PRIORITY);
      // System exit should be called only when NodeManager is instantiated from
      // main() funtion
      this.shouldExitOnShutdownEvent = true;
      this.init(conf);
      this.start();
    } catch (Throwable t) {
      LOG.fatal("Error starting NodeManager", t);
      System.exit(-1);
    }
  }

};