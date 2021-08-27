//,temp,sample_2598.java,2,14,temp,sample_2599.java,2,15
//,3
public class xxx {
public void test() throws Exception {
URL url = ObjectHelper.loadResourceAsURL("org/apache/camel/itest/CamelJacksonFallbackConverterTest.xml", CamelJacksonFallbackConverterTest.class.getClassLoader());
installBlueprintAsBundle("CamelJacksonFallbackConverterTest", url, true);
CamelContext camel = getOsgiService(bundleContext, CamelContext.class);
camel.getProperties().put(JacksonConstants.ENABLE_TYPE_CONVERTER, "true");
camel.getProperties().put(JacksonConstants.TYPE_CONVERTER_TO_POJO, "true");
final Pojo pojo = new Pojo(1337, "Constantine");
final DefaultExchange exchange = new DefaultExchange(camel);
final String string = camel.getTypeConverter().mandatoryConvertTo(String.class, exchange, pojo);


log.info("pojo string");
}

};