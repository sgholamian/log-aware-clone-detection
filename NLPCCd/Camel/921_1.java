//,temp,sample_8249.java,2,17,temp,sample_8244.java,2,17
//,3
public class xxx {
public void dummy_method(){
EventFactory eventFactory = getSingleBeanOfType(applicationContext, EventFactory.class);
if (eventFactory != null) {
camelContext.getManagementStrategy().setEventFactory(eventFactory);
}
UnitOfWorkFactory unitOfWorkFactory = getSingleBeanOfType(applicationContext, UnitOfWorkFactory.class);
if (unitOfWorkFactory != null) {
camelContext.setUnitOfWorkFactory(unitOfWorkFactory);
}
RuntimeEndpointRegistry runtimeEndpointRegistry = getSingleBeanOfType(applicationContext, RuntimeEndpointRegistry.class);
if (runtimeEndpointRegistry != null) {


log.info("using custom runtimeendpointregistry");
}
}

};