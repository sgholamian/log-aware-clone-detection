//,temp,sample_8344.java,2,17,temp,sample_8338.java,2,17
//,3
public class xxx {
public void dummy_method(){
ManagementNamingStrategy managementNamingStrategy = getBeanForType(ManagementNamingStrategy.class);
if (managementNamingStrategy != null) {
getContext().getManagementStrategy().setManagementNamingStrategy(managementNamingStrategy);
}
EventFactory eventFactory = getBeanForType(EventFactory.class);
if (eventFactory != null) {
getContext().getManagementStrategy().setEventFactory(eventFactory);
}
UnitOfWorkFactory unitOfWorkFactory = getBeanForType(UnitOfWorkFactory.class);
if (unitOfWorkFactory != null) {


log.info("using custom unitofworkfactory");
}
}

};