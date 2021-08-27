//,temp,sample_2294.java,2,16,temp,sample_2286.java,2,16
//,3
public class xxx {
public void dummy_method(){
MutableBeanMetadata regProcessor = context.createMetadata(MutableBeanMetadata.class);
regProcessor.setId(".camelBlueprint.processor.registry." + contextId);
regProcessor.setRuntimeClass(CamelDependenciesFinder.class);
regProcessor.setFactoryComponent(regProcessorFactory);
regProcessor.setFactoryMethod("call");
regProcessor.setProcessor(true);
regProcessor.addDependsOn(".camelBlueprint.processor.bean." + contextId);
regProcessor.addProperty("blueprintContainer", createRef(context, "blueprintContainer"));
context.getComponentDefinitionRegistry().registerComponentDefinition(regProcessor);
injectNamespaces(element, binder);


log.info("parsing camelcontext done returning");
}

};