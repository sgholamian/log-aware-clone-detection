//,temp,MountdBase.java,78,94,temp,MountdBase.java,59,75
//,2
public class xxx {
  private void startUDPServer() {
    SimpleUdpServer udpServer = new SimpleUdpServer(rpcProgram.getPort(),
        rpcProgram, 1);
    rpcProgram.startDaemons();
    try {
      udpServer.run();
    } catch (Throwable e) {
      LOG.fatal("Failed to start the UDP server.", e);
      if (udpServer.getBoundPort() > 0) {
        rpcProgram.unregister(PortmapMapping.TRANSPORT_UDP,
            udpServer.getBoundPort());
      }
      udpServer.shutdown();
      terminate(1, e);
    }
    udpBoundPort = udpServer.getBoundPort();
  }

};