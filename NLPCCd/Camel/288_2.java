//,temp,sample_5307.java,2,13,temp,sample_7714.java,2,17
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


log.info("cannot shutdown scheduler as there are still jobs registered");
}
}
}
}

};