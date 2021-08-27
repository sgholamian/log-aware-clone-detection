//,temp,sample_8241.java,2,17,temp,sample_8245.java,2,17
//,3
public class xxx {
public void dummy_method(){
InflightRepository inflightRepository = getSingleBeanOfType(applicationContext, InflightRepository.class);
if (inflightRepository != null) {
camelContext.setInflightRepository(inflightRepository);
}
AsyncProcessorAwaitManager asyncProcessorAwaitManager = getSingleBeanOfType(applicationContext, AsyncProcessorAwaitManager.class);
if (asyncProcessorAwaitManager != null) {
camelContext.setAsyncProcessorAwaitManager(asyncProcessorAwaitManager);
}
ManagementStrategy managementStrategy = getSingleBeanOfType(applicationContext, ManagementStrategy.class);
if (managementStrategy != null) {


log.info("using custom managementstrategy");
}
}

};