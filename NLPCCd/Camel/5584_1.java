//,temp,sample_5306.java,2,12,temp,sample_7715.java,2,18
//,3
public class xxx {
protected void doStop() throws Exception {
super.doStop();
if (scheduler != null) {
AtomicInteger number = (AtomicInteger) scheduler.getContext().get("CamelJobs");
if (number != null && number.get() > 0) {


log.info("cannot shutdown quartz scheduler as there are still jobs registered");
}
}
}

};