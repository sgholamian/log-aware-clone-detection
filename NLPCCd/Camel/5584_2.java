//,temp,sample_5306.java,2,12,temp,sample_7715.java,2,18
//,3
public class xxx {
protected void doStop() throws Exception {
super.doStop();
if (scheduler != null) {
if (isInterruptJobsOnShutdown()) {
scheduler.shutdown(false);
scheduler = null;
} else {
AtomicInteger number = (AtomicInteger) scheduler.getContext().get(QuartzConstants.QUARTZ_CAMEL_JOBS_COUNT);
if (number != null && number.get() > 0) {
} else {


log.info("shutting down scheduler will wait for all jobs to complete first");
}
}
}
}

};