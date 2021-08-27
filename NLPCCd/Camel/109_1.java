//,temp,sample_3414.java,2,16,temp,sample_3888.java,4,17
//,3
public class xxx {
public boolean resumeOrStartConsumer(Consumer consumer) throws Exception {
if (consumer instanceof Suspendable) {
boolean resumed = ServiceHelper.resumeService(consumer);
if (resumed) {
} else {
}
return resumed;
}
if (!ServiceHelper.isStarted(consumer)) {
ServiceHelper.startService(consumer);


log.info("started consumer");
}
}

};