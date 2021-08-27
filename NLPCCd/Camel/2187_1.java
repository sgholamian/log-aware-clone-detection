//,temp,sample_6236.java,2,15,temp,sample_4332.java,2,15
//,2
public class xxx {
public void parse() throws Exception {
JavaClassSource clazz = (JavaClassSource) Roaster.parse(new File("src/test/java/org/apache/camel/parser/java/MyConcatFieldRouteBuilder.java"));
MethodSource<JavaClassSource> method = clazz.getMethod("configure");
List<ParserResult> list = CamelJavaParserHelper.parseCamelConsumerUris(method, true, true);
for (ParserResult result : list) {
}
Assert.assertEquals("ftp:localhost:{{ftpPort}}/myapp?password=admin&username=admin", list.get(0).getElement());
list = CamelJavaParserHelper.parseCamelProducerUris(method, true, true);
for (ParserResult result : list) {


log.info("producer");
}
}

};