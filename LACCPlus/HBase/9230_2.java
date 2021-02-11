//,temp,TestThriftServerCmdLine.java,142,160,temp,TestThriftHttpServer.java,114,127
//,3
public class xxx {
  private void startHttpServerThread(final String[] args) {
    LOG.info("Starting HBase Thrift server with HTTP server: " + Joiner.on(" ").join(args));

    httpServerException = null;
    httpServerThread = new Thread(() -> {
      try {
        thriftServer.run(args);
      } catch (Exception e) {
        httpServerException = e;
      }
    });
    httpServerThread.setName(ThriftServer.class.getSimpleName() + "-httpServer");
    httpServerThread.start();
  }

};