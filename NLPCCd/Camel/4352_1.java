//,temp,sample_5395.java,2,13,temp,sample_1495.java,2,11
//,3
public class xxx {
public static void getEndpointProperties(FacebookEndpointConfiguration configuration, Map<String, Object> properties) {
if (IntrospectionSupport.getProperties(configuration, properties, null, false)) {
final Set<String> names = properties.keySet();
names.removeAll(COMPONENT_CONFIG_FIELDS);
}
if (LOG.isDebugEnabled()) {
final Set<String> names = properties.keySet();


log.info("found endpoint properties");
}
}

};