//,temp,sample_8369.java,2,13,temp,sample_8370.java,2,17
//,3
public class xxx {
public void dummy_method(){
ModelJAXBContextFactory modelJAXBContextFactory = getBeanForType(ModelJAXBContextFactory.class);
if (modelJAXBContextFactory != null) {
getContext().setModelJAXBContextFactory(modelJAXBContextFactory);
}
ClassResolver classResolver = getBeanForType(ClassResolver.class);
if (classResolver != null) {
getContext().setClassResolver(classResolver);
}
FactoryFinderResolver factoryFinderResolver = getBeanForType(FactoryFinderResolver.class);
if (factoryFinderResolver != null) {


log.info("using custom factoryfinderresolver");
}
}

};