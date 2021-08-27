//,temp,sample_1581.java,2,14,temp,sample_1923.java,2,14
//,3
public class xxx {
public void parse() throws Exception {
JavaClassSource clazz = (JavaClassSource) Roaster.parse(new File("src/test/java/org/apache/camel/parser/java/MySimpleToFRoute.java"));
MethodSource<JavaClassSource> method = CamelJavaParserHelper.findConfigureMethod(clazz);
List<CamelEndpointDetails> details = new ArrayList<>();
RouteBuilderParser.parseRouteBuilderEndpoints(clazz, ".", "src/test/java/org/apache/camel/parser/java/MySimpleToFRoute.java", details);
LOG.info("{}", details);
List<ParserResult> list = CamelJavaParserHelper.parseCamelConsumerUris(method, true, true);
for (ParserResult result : list) {


log.info("consumer");
}
}

};