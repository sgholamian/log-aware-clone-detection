//,temp,sample_3459.java,2,19,temp,sample_2005.java,2,19
//,2
public class xxx {
public void dummy_method(){
SalesforceUpsertContactComponent connector = new SalesforceUpsertContactComponent();
connector.setCamelContext(camelContext);
Map<String, Object> parameters = new HashMap<>();
IntrospectionSupport.getProperties(configuration, parameters, null, false);
CamelPropertiesHelper.setCamelProperties(camelContext, connector, parameters, false);
connector.setOptions(parameters);
if (ObjectHelper.isNotEmpty(customizers)) {
for (ConnectorCustomizer<SalesforceUpsertContactComponent> customizer : customizers) {
boolean useCustomizer = (customizer instanceof HasId) ? HierarchicalPropertiesEvaluator .evaluate( applicationContext.getEnvironment(), "camel.connector.customizer", "camel.connector.salesforce-upsert-contact.customizer", ((HasId) customizer).getId()) : HierarchicalPropertiesEvaluator .evaluate(applicationContext.getEnvironment(), "camel.connector.customizer", "camel.connector.salesforce-upsert-contact.customizer");
if (useCustomizer) {


log.info("configure connector with customizer");
}
}
}
}

};