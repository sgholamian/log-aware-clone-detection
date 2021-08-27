//,temp,sample_6527.java,2,14,temp,sample_2925.java,2,14
//,3
public class xxx {
public void parse() throws Exception {
JavaClassSource clazz = (JavaClassSource) Roaster.parse(new File("src/test/java/org/apache/camel/parser/java/SimpleProcessorTest.java"));
MethodSource<JavaClassSource> method = CamelJavaParserHelper.findConfigureMethod(clazz);
List<CamelEndpointDetails> details = new ArrayList<CamelEndpointDetails>();
RouteBuilderParser.parseRouteBuilderEndpoints(clazz, ".", "src/test/java/org/apache/camel/parser/java/SimpleProcessorTest.java", details);
LOG.info("{}", details);
List<ParserResult> list = CamelJavaParserHelper.parseCamelConsumerUris(method, true, true);
for (ParserResult result : list) {


log.info("consumer");
}
}

};