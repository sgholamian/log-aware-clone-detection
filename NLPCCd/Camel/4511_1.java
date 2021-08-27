//,temp,sample_6012.java,2,17,temp,sample_6011.java,2,17
//,2
public class xxx {
public void dummy_method(){
for (EventNotifier notifier : getManagementStrategy().getEventNotifiers()) {
shutdownServices(notifier);
}
shutdownServices(executorServiceManager);
shutdownServices(managementStrategy);
shutdownServices(managementMBeanAssembler);
shutdownServices(lifecycleStrategies);
forceStopLazyInitialization();
IntrospectionSupport.stop();
if (log.isInfoEnabled()) {


log.info("apache camel camelcontext is shutdown in");
}
}

};