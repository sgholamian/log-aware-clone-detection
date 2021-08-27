//,temp,sample_8371.java,2,17,temp,sample_8342.java,2,17
//,2
public class xxx {
public void dummy_method(){
AsyncProcessorAwaitManager asyncProcessorAwaitManager = getBeanForType(AsyncProcessorAwaitManager.class);
if (asyncProcessorAwaitManager != null) {
getContext().setAsyncProcessorAwaitManager(asyncProcessorAwaitManager);
}
ManagementStrategy managementStrategy = getBeanForType(ManagementStrategy.class);
if (managementStrategy != null) {
getContext().setManagementStrategy(managementStrategy);
}
ManagementNamingStrategy managementNamingStrategy = getBeanForType(ManagementNamingStrategy.class);
if (managementNamingStrategy != null) {


log.info("using custom managementnamingstrategy");
}
}

};