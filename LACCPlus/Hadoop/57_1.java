//,temp,Nfs3Base.java,48,61,temp,MountdBase.java,96,110
//,3
public class xxx {
  public void start(boolean register) {
    startTCPServer(); // Start TCP server

    if (register) {
      ShutdownHookManager.get().addShutdownHook(new NfsShutdownHook(),
          SHUTDOWN_HOOK_PRIORITY);
      try {
        rpcProgram.register(PortmapMapping.TRANSPORT_TCP, nfsBoundPort);
      } catch (Throwable e) {
        LOG.fatal("Failed to register the NFSv3 service.", e);
        terminate(1, e);
      }
    }
  }

};