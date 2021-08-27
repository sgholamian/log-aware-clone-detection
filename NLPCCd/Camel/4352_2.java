//,temp,sample_5395.java,2,13,temp,sample_1495.java,2,11
//,3
public class xxx {
public void getEndpointProperties(Object endpointConfiguration, Map<String, Object> properties) {
Set<String> names = null;
if (IntrospectionSupport.getProperties(endpointConfiguration, properties, null, false)) {
names = properties.keySet();
names.removeAll(componentConfigFields);
}


log.info("found endpoint properties");
}

};