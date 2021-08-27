//,temp,sample_5307.java,2,13,temp,sample_7714.java,2,17
//,3
public class xxx {
protected void doStop() throws Exception {
super.doStop();
if (scheduler != null) {
AtomicInteger number = (AtomicInteger) scheduler.getContext().get("CamelJobs");
if (number != null && number.get() > 0) {
} else {


log.info("there are no more jobs registered so shutting down quartz scheduler");
}
}
}

};