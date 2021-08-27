//,temp,sample_8369.java,2,13,temp,sample_8370.java,2,17
//,3
public class xxx {
private void setupCustomServices() {
ModelJAXBContextFactory modelJAXBContextFactory = getBeanForType(ModelJAXBContextFactory.class);
if (modelJAXBContextFactory != null) {
getContext().setModelJAXBContextFactory(modelJAXBContextFactory);
}
ClassResolver classResolver = getBeanForType(ClassResolver.class);
if (classResolver != null) {


log.info("using custom classresolver");
}
}

};