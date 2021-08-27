//,temp,sample_8087.java,2,15,temp,sample_6528.java,2,17
//,3
public class xxx {
public void parse() throws Exception {
JavaClassSource clazz = (JavaClassSource) Roaster.parse(new File("src/test/java/org/apache/camel/parser/java/MyRouteTest.java"));
MethodSource<JavaClassSource> method = CamelJavaParserHelper.findConfigureMethod(clazz);
List<ParserResult> list = CamelJavaParserHelper.parseCamelConsumerUris(method, true, false);
for (ParserResult result : list) {
}
Assert.assertEquals("direct:foo", list.get(0).getElement());
list = CamelJavaParserHelper.parseCamelProducerUris(method, true, false);
for (ParserResult result : list) {


log.info("producer");
}
}

};