//,temp,sample_5155.java,2,15,temp,sample_5063.java,2,15
//,3
public class xxx {
public void parse() throws Exception {
JavaClassSource clazz = (JavaClassSource) Roaster.parse(new File("src/test/java/org/apache/camel/parser/java/MyRouteDuplicateIdTest.java"));
MethodSource<JavaClassSource> method = CamelJavaParserHelper.findConfigureMethod(clazz);
List<ParserResult> list = CamelJavaParserHelper.parseCamelConsumerUris(method, true, false);
for (ParserResult result : list) {
}
Assert.assertEquals(3, list.size());
list = CamelJavaParserHelper.parseCamelRouteIds(method);
for (ParserResult result : list) {


log.info("route id");
}
}

};