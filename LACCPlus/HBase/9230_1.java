//,temp,TestThriftServerCmdLine.java,142,160,temp,TestThriftHttpServer.java,114,127
//,3
public class xxx {
  private void startCmdLineThread(final String[] args) {
    LOG.info("Starting HBase Thrift server with command line: " + Joiner.on(" ").join(args));

    cmdLineException = null;
    cmdLineThread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          thriftServer.run(args);
        } catch (Exception e) {
          LOG.error("Error when start thrift server", e);
          cmdLineException = e;
        }
      }
    });
    cmdLineThread.setName(ThriftServer.class.getSimpleName() +
        "-cmdline");
    cmdLineThread.start();
  }

};