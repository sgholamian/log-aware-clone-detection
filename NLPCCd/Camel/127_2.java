//,temp,sample_7512.java,2,10,temp,sample_3409.java,2,11
//,3
public class xxx {
public boolean suspendOrStopConsumer(Consumer consumer) throws Exception {
if (consumer instanceof Suspendable) {
boolean suspended = ServiceHelper.suspendService(consumer);
if (suspended) {


log.info("suspended consumer");
}
}
}

};