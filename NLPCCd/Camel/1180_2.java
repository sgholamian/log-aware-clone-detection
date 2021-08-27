//,temp,sample_8341.java,2,17,temp,sample_8343.java,2,17
//,3
public class xxx {
public void dummy_method(){
ManagementStrategy managementStrategy = getBeanForType(ManagementStrategy.class);
if (managementStrategy != null) {
getContext().setManagementStrategy(managementStrategy);
}
ManagementNamingStrategy managementNamingStrategy = getBeanForType(ManagementNamingStrategy.class);
if (managementNamingStrategy != null) {
getContext().getManagementStrategy().setManagementNamingStrategy(managementNamingStrategy);
}
EventFactory eventFactory = getBeanForType(EventFactory.class);
if (eventFactory != null) {


log.info("using custom eventfactory");
}
}

};