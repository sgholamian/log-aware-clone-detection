//,temp,RoasterMyFieldMethodCallRouteBuilderConfigureTest.java,37,57,temp,RoasterNewLineConstRouteBuilderConfigureTest.java,37,55
//,3
public class xxx {
    @Test
    void parse() throws Exception {
        JavaClassSource clazz = (JavaClassSource) Roaster
                .parse(new File("src/test/java/org/apache/camel/parser/java/MyNewLineConstRouteBuilder.java"));
        MethodSource<JavaClassSource> method = clazz.getMethod("configure");

        List<ParserResult> list = CamelJavaParserHelper.parseCamelConsumerUris(method, true, false);
        for (ParserResult result : list) {
            LOG.info("Consumer: " + result.getElement());
        }
        assertEquals("timer:foo", list.get(0).getElement());

        list = CamelJavaParserHelper.parseCamelProducerUris(method, true, false);
        for (ParserResult result : list) {
            LOG.info("Producer: " + result.getElement());
        }
        assertEquals("file:output?fileExist=Append&chmod=777&allowNullBody=true", list.get(0).getElement());
        assertEquals("log:b", list.get(1).getElement());
    }

};