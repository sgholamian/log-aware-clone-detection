//,temp,sample_5155.java,2,15,temp,sample_5063.java,2,15
//,3
public class xxx {
public void parse() throws Exception {
JavaClassSource clazz = (JavaClassSource) Roaster.parse(new File("src/test/java/org/apache/camel/parser/java/MyLocalAddRouteBuilderTest.java"));
MethodSource<JavaClassSource> method = CamelJavaParserHelper.findConfigureMethod(clazz);
Assert.assertNull(method);
List<MethodSource<JavaClassSource>> methods = CamelJavaParserHelper.findInlinedConfigureMethods(clazz);
Assert.assertEquals(1, methods.size());
method = methods.get(0);
List<ParserResult> list = CamelJavaParserHelper.parseCamelConsumerUris(method, true, true);
for (ParserResult result : list) {


log.info("consumer");
}
}

};