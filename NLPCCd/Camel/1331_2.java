//,temp,sample_8243.java,2,17,temp,sample_8247.java,2,17
//,3
public class xxx {
public void dummy_method(){
ManagementStrategy managementStrategy = getSingleBeanOfType(applicationContext, ManagementStrategy.class);
if (managementStrategy != null) {
camelContext.setManagementStrategy(managementStrategy);
}
ManagementNamingStrategy managementNamingStrategy = getSingleBeanOfType(applicationContext, ManagementNamingStrategy.class);
if (managementNamingStrategy != null) {
camelContext.getManagementStrategy().setManagementNamingStrategy(managementNamingStrategy);
}
EventFactory eventFactory = getSingleBeanOfType(applicationContext, EventFactory.class);
if (eventFactory != null) {


log.info("using custom eventfactory");
}
}

};