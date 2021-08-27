//,temp,sample_7510.java,2,11,temp,sample_7513.java,2,11
//,2
public class xxx {
protected static void suspendNow(Consumer consumer) {
try {
ServiceHelper.suspendService(consumer);
} catch (Throwable e) {
EventHelper.notifyServiceStopFailure(consumer.getEndpoint().getCamelContext(), consumer, e);
}


log.info("suspend complete for");
}

};