//,temp,HBaseTimelineReaderImpl.java,105,120,temp,AppLevelTimelineCollectorWithAgg.java,99,109
//,3
public class xxx {
  @Override
  protected void serviceStop() throws Exception {
    appAggregationExecutor.shutdown();
    if (!appAggregationExecutor.awaitTermination(10, TimeUnit.SECONDS)) {
      LOG.info("App-level aggregator shutdown timed out, shutdown now. ");
      appAggregationExecutor.shutdownNow();
    }
    // Perform one round of aggregation after the aggregation executor is done.
    appAggregator.aggregate();
    super.serviceStop();
  }

};