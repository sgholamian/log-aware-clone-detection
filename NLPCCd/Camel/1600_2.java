//,temp,sample_5302.java,2,15,temp,sample_3984.java,2,15
//,3
public class xxx {
public void parse() throws Exception {
JavaClassSource clazz = (JavaClassSource) Roaster.parse(new File("src/test/java/org/apache/camel/parser/java/MyNewLineRouteBuilder.java"));
MethodSource<JavaClassSource> method = clazz.getMethod("configure");
List<ParserResult> list = CamelJavaParserHelper.parseCamelConsumerUris(method, true, false);
for (ParserResult result : list) {
}
Assert.assertEquals("timer:foo", list.get(0).getElement());
list = CamelJavaParserHelper.parseCamelProducerUris(method, true, false);
for (ParserResult result : list) {


log.info("producer");
}
}

};