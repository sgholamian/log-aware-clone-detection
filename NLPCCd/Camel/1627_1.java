//,temp,sample_2294.java,2,16,temp,sample_2286.java,2,16
//,3
public class xxx {
public void dummy_method(){
factory2.setFactoryMethod("call");
factory2.setInitMethod("afterPropertiesSet");
factory2.setDestroyMethod("destroy");
factory2.addProperty("blueprintContainer", createRef(context, "blueprintContainer"));
MutableBeanMetadata ctx = context.createMetadata(MutableBeanMetadata.class);
ctx.setId(id);
ctx.setRuntimeClass(KeyStoreParameters.class);
ctx.setFactoryComponent(factory2);
ctx.setFactoryMethod("getObject");
ctx.setActivation(ACTIVATION_LAZY);


log.info("parsing keystoreparameters done returning");
}

};