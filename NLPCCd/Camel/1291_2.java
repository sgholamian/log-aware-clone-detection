//,temp,sample_5154.java,2,11,temp,sample_6235.java,2,11
//,3
public class xxx {
public void parse() throws Exception {
JavaClassSource clazz = (JavaClassSource) Roaster.parse(new File("src/test/java/org/apache/camel/parser/java/MyConcatFieldRouteBuilder.java"));
MethodSource<JavaClassSource> method = clazz.getMethod("configure");
List<ParserResult> list = CamelJavaParserHelper.parseCamelConsumerUris(method, true, true);
for (ParserResult result : list) {


log.info("consumer");
}
}

};