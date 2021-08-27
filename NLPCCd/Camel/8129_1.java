//,temp,sample_1047.java,2,16,temp,sample_1048.java,2,16
//,3
public class xxx {
public void dummy_method(){
URL blueprintUrl = ObjectHelper.loadResourceAsURL("org/apache/camel/itest/CamelTypeConverterTest.xml", CamelTypeConverterTest.class.getClassLoader());
installBlueprintAsBundle("CamelTypeConverterTest", blueprintUrl, true, bundle -> {
((TinyBundle) bundle) .add("META-INF/services/org/apache/camel/TypeConverter", new ByteArrayInputStream("org.apache.camel.itest.typeconverter.MyConverter".getBytes())) .add(MyConverter.class, InnerClassStrategy.NONE) .set(Constants.DYNAMICIMPORT_PACKAGE, "*");
});
CamelContext camel = getOsgiService(bundleContext, CamelContext.class);
final Pojo pojo = new Pojo();
String pojoName = "Constantine";
pojo.setName(pojoName);
final DefaultExchange exchange = new DefaultExchange(camel);
final String string = camel.getTypeConverter().mandatoryConvertTo(String.class, exchange, pojo);


log.info("pojo string");
}

};