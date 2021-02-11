//,temp,TestSecureRESTServer.java,235,258,temp,TestSpnegoHttpServer.java,120,136
//,3
public class xxx {
  @AfterClass
  public static void stopServer() throws Exception {
    try {
      if (null != server) {
        server.stop();
      }
    } catch (Exception e) {
      LOG.info("Failed to stop info server", e);
    }
    try {
      if (CLUSTER != null) {
        CLUSTER.shutdown();
      }
    } catch (Exception e) {
      LOG.info("Failed to stop HBase cluster", e);
    }
    try {
      if (null != KDC) {
        KDC.stop();
      }
    } catch (Exception e) {
      LOG.info("Failed to stop mini KDC", e);
    }
  }

};