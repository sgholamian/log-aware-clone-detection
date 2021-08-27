//,temp,sample_8316.java,2,11,temp,sample_1542.java,2,11
//,3
public class xxx {
public static void resolvePropertyPlaceholders(CamelContext camelContext, Object target) throws Exception {
Map<String, Object> properties = new HashMap<String, Object>();
IntrospectionSupport.getProperties(target, properties, null);
Map<String, Object> changedProperties = new HashMap<String, Object>();
if (!properties.isEmpty()) {


log.info("there are properties on");
}
}

};