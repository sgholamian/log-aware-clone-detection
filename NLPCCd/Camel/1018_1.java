//,temp,sample_3983.java,2,11,temp,sample_516.java,2,11
//,3
public class xxx {
public void parse() throws Exception {
JavaClassSource clazz = (JavaClassSource) Roaster.parse(new File("src/test/java/org/apache/camel/parser/java/MyNewLineRouteBuilder.java"));
MethodSource<JavaClassSource> method = clazz.getMethod("configure");
List<ParserResult> list = CamelJavaParserHelper.parseCamelConsumerUris(method, true, false);
for (ParserResult result : list) {


log.info("consumer");
}
}

};