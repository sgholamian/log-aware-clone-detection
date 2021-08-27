//,temp,sample_6083.java,2,19,temp,sample_7854.java,2,19
//,2
public class xxx {
public void dummy_method(){
PetStoreComponent connector = new PetStoreComponent();
connector.setCamelContext(camelContext);
Map<String, Object> parameters = new HashMap<>();
IntrospectionSupport.getProperties(configuration, parameters, null, false);
CamelPropertiesHelper.setCamelProperties(camelContext, connector, parameters, false);
connector.setOptions(parameters);
if (ObjectHelper.isNotEmpty(customizers)) {
for (ConnectorCustomizer<PetStoreComponent> customizer : customizers) {
boolean useCustomizer = (customizer instanceof HasId) ? HierarchicalPropertiesEvaluator.evaluate( applicationContext.getEnvironment(), "camel.connector.customizer", "camel.connector.petstore.customizer", ((HasId) customizer).getId()) : HierarchicalPropertiesEvaluator.evaluate( applicationContext.getEnvironment(), "camel.connector.customizer", "camel.connector.petstore.customizer");
if (useCustomizer) {


log.info("configure connector with customizer");
}
}
}
}

};