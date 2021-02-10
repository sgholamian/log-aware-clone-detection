//,temp,RegistryDNSServer.java,236,254,temp,SharedCacheManager.java,148,163
//,3
public class xxx {
  static RegistryDNSServer launchDNSServer(Configuration conf,
      RegistryDNS rdns) {
    RegistryDNSServer dnsServer = null;

    Thread
        .setDefaultUncaughtExceptionHandler(new YarnUncaughtExceptionHandler());
    try {
      dnsServer = new RegistryDNSServer("RegistryDNSServer", rdns);
      ShutdownHookManager.get().addShutdownHook(
          new CompositeService.CompositeServiceShutdownHook(dnsServer),
          SHUTDOWN_HOOK_PRIORITY);
      dnsServer.init(conf);
      dnsServer.start();
    } catch (Throwable t) {
      LOG.error("Error starting Registry DNS Server", t);
      ExitUtil.terminate(-1, "Error starting Registry DNS Server");
    }
    return dnsServer;
  }

};