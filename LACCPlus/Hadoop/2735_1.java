//,temp,HBaseTimelineReaderImpl.java,105,120,temp,AppLevelTimelineCollectorWithAgg.java,99,109
//,3
public class xxx {
  @Override
  protected void serviceStop() throws Exception {
    if (conn != null) {
      LOG.info("closing the hbase Connection");
      conn.close();
    }
    if (monitorExecutorService != null) {
      monitorExecutorService.shutdownNow();
      if (!monitorExecutorService.awaitTermination(30, TimeUnit.SECONDS)) {
        LOG.warn("failed to stop the monitir task in time. " +
            "will still proceed to close the monitor.");
      }
    }
    monitorConn.close();
    super.serviceStop();
  }

};