//,temp,sample_7105.java,2,13,temp,sample_7106.java,2,16
//,3
public class xxx {
private void initJmx(ServletCamelContext camelContext, Map<String, Object> parameters) throws Exception {
Map<String, Object> properties = IntrospectionSupport.extractProperties(parameters, "jmx.");
if (properties != null && !properties.isEmpty()) {
String disabled = (String) properties.remove("disabled");
boolean disableJmx = CamelContextHelper.parseBoolean(camelContext, disabled != null ? disabled : "false");
if (disableJmx) {


log.info("jmxagent disabled");
}
}
}

};