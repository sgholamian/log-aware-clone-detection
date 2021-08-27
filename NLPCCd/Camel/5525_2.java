//,temp,sample_4993.java,2,11,temp,sample_2265.java,2,13
//,3
public class xxx {
protected void doStart() throws Exception {
ObjectHelper.notNull(executorService, "executorService", this);
ObjectHelper.notNull(scheduledExecutorService, "scheduledExecutorService", this);
ObjectHelper.notNull(endpoint, "endpoint", this);
correlation = new CorrelationTimeoutMap(scheduledExecutorService, endpoint.getRequestTimeoutCheckerInterval(), executorService);
ServiceHelper.startService(correlation);
listenerContainer = createListenerContainer();
listenerContainer.afterPropertiesSet();


log.info("starting reply listener container on endpoint");
}

};