//,temp,sample_5154.java,2,11,temp,sample_6235.java,2,11
//,3
public class xxx {
public void parse() throws Exception {
JavaClassSource clazz = (JavaClassSource) Roaster.parse(new File("src/test/java/org/apache/camel/parser/java/MyRouteDuplicateIdTest.java"));
MethodSource<JavaClassSource> method = CamelJavaParserHelper.findConfigureMethod(clazz);
List<ParserResult> list = CamelJavaParserHelper.parseCamelConsumerUris(method, true, false);
for (ParserResult result : list) {


log.info("consumer");
}
}

};