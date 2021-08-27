//,temp,sample_2290.java,2,16,temp,sample_2288.java,2,16
//,2
public class xxx {
public void dummy_method(){
factory2.setId(".camelBlueprint.factory." + id);
factory2.setFactoryComponent(factory);
factory2.setFactoryMethod("call");
MutableBeanMetadata ctx = context.createMetadata(MutableBeanMetadata.class);
ctx.setId(id);
ctx.setRuntimeClass(List.class);
ctx.setFactoryComponent(factory2);
ctx.setFactoryMethod("getRests");
ctx.setActivation(ACTIVATION_LAZY);
injectNamespaces(element, binder);


log.info("parsing restcontext done returning");
}

};