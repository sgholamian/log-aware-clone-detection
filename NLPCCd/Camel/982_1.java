//,temp,sample_2004.java,2,11,temp,sample_5406.java,2,11
//,3
public class xxx {
public void parse() throws Exception {
JavaClassSource clazz = (JavaClassSource) Roaster.parse(new File("src/test/java/org/apache/camel/parser/java/MySimpleRouteBuilder.java"));
MethodSource<JavaClassSource> method = clazz.getMethod("configure");
List<ParserResult> list = CamelJavaParserHelper.parseCamelSimpleExpressions(method);
for (ParserResult simple : list) {


log.info("line");
}
}

};