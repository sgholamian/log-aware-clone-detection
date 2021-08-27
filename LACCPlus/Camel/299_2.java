//,temp,RoasterCdiRouteBuilderConfigureTest.java,37,55,temp,RoasterFieldRouteBuilderConfigureTest.java,37,55
//,2
public class xxx {
    @Test
    void parse() throws Exception {
        JavaClassSource clazz = (JavaClassSource) Roaster
                .parse(new File("src/test/java/org/apache/camel/parser/java/MyFieldRouteBuilder.java"));
        MethodSource<JavaClassSource> method = clazz.getMethod("configure");

        List<ParserResult> list = CamelJavaParserHelper.parseCamelConsumerUris(method, true, true);
        for (ParserResult result : list) {
            LOG.info("Consumer: " + result.getElement());
        }
        assertEquals("timer:foo", list.get(0).getElement());

        list = CamelJavaParserHelper.parseCamelProducerUris(method, true, true);
        for (ParserResult result : list) {
            LOG.info("Producer: " + result.getElement());
        }
        assertEquals("file:output?fileExist=Override", list.get(0).getElement());
        assertEquals("log:b", list.get(1).getElement());
    }

};