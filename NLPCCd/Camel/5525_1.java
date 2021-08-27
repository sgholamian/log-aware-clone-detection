//,temp,sample_4993.java,2,11,temp,sample_2265.java,2,13
//,3
public class xxx {
protected void doStart() throws Exception {
ObjectHelper.notNull(executorService, "executorService", this);
ObjectHelper.notNull(endpoint, "endpoint", this);
correlation = new CorrelationTimeoutMap(executorService, endpoint.getRequestTimeoutCheckerInterval());
ServiceHelper.startService(correlation);
listenerContainer = createListenerContainer();


log.info("using executor");
}

};