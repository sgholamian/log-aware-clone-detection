//,temp,sample_8341.java,2,17,temp,sample_8343.java,2,17
//,3
public class xxx {
public void dummy_method(){
InflightRepository inflightRepository = getBeanForType(InflightRepository.class);
if (inflightRepository != null) {
getContext().setInflightRepository(inflightRepository);
}
AsyncProcessorAwaitManager asyncProcessorAwaitManager = getBeanForType(AsyncProcessorAwaitManager.class);
if (asyncProcessorAwaitManager != null) {
getContext().setAsyncProcessorAwaitManager(asyncProcessorAwaitManager);
}
ManagementStrategy managementStrategy = getBeanForType(ManagementStrategy.class);
if (managementStrategy != null) {


log.info("using custom managementstrategy");
}
}

};