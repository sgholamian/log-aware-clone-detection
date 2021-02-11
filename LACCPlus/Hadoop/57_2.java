//,temp,Nfs3Base.java,48,61,temp,MountdBase.java,96,110
//,3
public class xxx {
  public void start(boolean register) {
    startUDPServer();
    startTCPServer();
    if (register) {
      ShutdownHookManager.get().addShutdownHook(new Unregister(),
          SHUTDOWN_HOOK_PRIORITY);
      try {
        rpcProgram.register(PortmapMapping.TRANSPORT_UDP, udpBoundPort);
        rpcProgram.register(PortmapMapping.TRANSPORT_TCP, tcpBoundPort);
      } catch (Throwable e) {
        LOG.fatal("Failed to register the MOUNT service.", e);
        terminate(1, e);
      }
    }
  }

};