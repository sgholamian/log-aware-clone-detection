//,temp,sample_2586.java,2,15,temp,sample_2585.java,2,13
//,3
public class xxx {
private TransportClient createClient() throws Exception {
final Settings.Builder settings = getSettings();
final CamelContext camelContext = getEndpoint().getCamelContext();
final Class<?> clazz = camelContext.getClassResolver().resolveClass("org.elasticsearch.xpack.client.PreBuiltXPackTransportClient");
if (clazz != null) {
Constructor<?> ctor = clazz.getConstructor(Settings.class, Class[].class);
settings.put("xpack.security.user", configuration.getUser() + ":" + configuration.getPassword()) .put("xpack.security.transport.ssl.enabled", configuration.getEnableSSL());


log.info("xpack client was found on the classpath");
}
}

};