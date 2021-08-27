//,temp,sample_1924.java,2,17,temp,sample_2926.java,2,17
//,3
public class xxx {
public void dummy_method(){
MethodSource<JavaClassSource> method = CamelJavaParserHelper.findConfigureMethod(clazz);
List<CamelEndpointDetails> details = new ArrayList<CamelEndpointDetails>();
RouteBuilderParser.parseRouteBuilderEndpoints(clazz, ".", "src/test/java/org/apache/camel/parser/java/SimpleProcessorTest.java", details);
LOG.info("{}", details);
List<ParserResult> list = CamelJavaParserHelper.parseCamelConsumerUris(method, true, true);
for (ParserResult result : list) {
}
Assert.assertEquals("direct:start", list.get(0).getElement());
list = CamelJavaParserHelper.parseCamelProducerUris(method, true, true);
for (ParserResult result : list) {


log.info("producer");
}
}

};