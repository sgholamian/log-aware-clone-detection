//,temp,Nfs3Base.java,63,79,temp,MountdBase.java,59,75
//,2
public class xxx {
  private void startTCPServer() {
    SimpleTcpServer tcpServer = new SimpleTcpServer(rpcProgram.getPort(),
        rpcProgram, 0);
    rpcProgram.startDaemons();
    try {
      tcpServer.run();
    } catch (Throwable e) {
      LOG.error("Failed to start the TCP server.", e);
      if (tcpServer.getBoundPort() > 0) {
        rpcProgram.unregister(PortmapMapping.TRANSPORT_TCP,
            tcpServer.getBoundPort());
      }
      tcpServer.shutdown();
      terminate(1, e);
    }
    nfsBoundPort = tcpServer.getBoundPort();
  }

};