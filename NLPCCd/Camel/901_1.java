//,temp,sample_4936.java,2,16,temp,sample_5407.java,2,15
//,3
public class xxx {
public void parse() throws Exception {
JavaClassSource clazz = (JavaClassSource) Roaster.parse(new File("src/test/java/org/apache/camel/parser/java/MyRouteEmptyUriTest.java"));
MethodSource<JavaClassSource> method = CamelJavaParserHelper.findConfigureMethod(clazz);
List<ParserResult> list = CamelJavaParserHelper.parseCamelConsumerUris(method, true, false);
for (ParserResult result : list) {
}
Assert.assertEquals("direct:foo", list.get(0).getElement());
list = CamelJavaParserHelper.parseCamelProducerUris(method, true, false);
Assert.assertEquals(1, list.size());
for (ParserResult result : list) {


log.info("producer");
}
}

};