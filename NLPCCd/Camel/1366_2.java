//,temp,sample_8340.java,2,17,temp,sample_8346.java,2,17
//,3
public class xxx {
public void dummy_method(){
UnitOfWorkFactory unitOfWorkFactory = getBeanForType(UnitOfWorkFactory.class);
if (unitOfWorkFactory != null) {
getContext().setUnitOfWorkFactory(unitOfWorkFactory);
}
RuntimeEndpointRegistry runtimeEndpointRegistry = getBeanForType(RuntimeEndpointRegistry.class);
if (runtimeEndpointRegistry != null) {
getContext().setRuntimeEndpointRegistry(runtimeEndpointRegistry);
}
HeadersMapFactory headersMapFactory = getBeanForType(HeadersMapFactory.class);
if (headersMapFactory != null) {


log.info("using custom headersmapfactory");
}
}

};