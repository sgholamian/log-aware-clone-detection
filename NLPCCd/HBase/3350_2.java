//,temp,sample_2074.java,2,17,temp,sample_2073.java,2,17
//,3
public class xxx {
public void dummy_method(){
initBlockCache();
initMobFileCache();
this.period = regionServer.conf.getLong(HConstants.REGIONSERVER_METRICS_PERIOD, HConstants.DEFAULT_REGIONSERVER_METRICS_PERIOD);
this.executor = CompatibilitySingletonFactory.getInstance(MetricsExecutor.class).getExecutor();
this.runnable = new RegionServerMetricsWrapperRunnable();
this.executor.scheduleWithFixedDelay(this.runnable, this.period, this.period, TimeUnit.MILLISECONDS);
this.metricsWALSource = CompatibilitySingletonFactory.getInstance(MetricsWALSource.class);
try {
this.dfsHedgedReadMetrics = FSUtils.getDFSHedgedReadMetrics(regionServer.getConfiguration());
} catch (IOException e) {


log.info("failed to get hedged metrics");
}
}

};