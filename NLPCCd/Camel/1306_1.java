//,temp,sample_8345.java,2,17,temp,sample_8376.java,2,17
//,3
public class xxx {
public void dummy_method(){
EventFactory eventFactory = getBeanForType(EventFactory.class);
if (eventFactory != null) {
getContext().getManagementStrategy().setEventFactory(eventFactory);
}
UnitOfWorkFactory unitOfWorkFactory = getBeanForType(UnitOfWorkFactory.class);
if (unitOfWorkFactory != null) {
getContext().setUnitOfWorkFactory(unitOfWorkFactory);
}
RuntimeEndpointRegistry runtimeEndpointRegistry = getBeanForType(RuntimeEndpointRegistry.class);
if (runtimeEndpointRegistry != null) {


log.info("using custom runtimeendpointregistry");
}
}

};