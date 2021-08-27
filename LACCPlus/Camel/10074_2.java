//,temp,RoasterSplitTokenizeTest.java,40,76,temp,RoasterSimpleToDTest.java,40,74
//,3
public class xxx {
    @Test
    void parse() throws Exception {
        JavaClassSource clazz
                = (JavaClassSource) Roaster.parse(new File("src/test/java/org/apache/camel/parser/java/MySimpleToDRoute.java"));
        MethodSource<JavaClassSource> method = CamelJavaParserHelper.findConfigureMethod(clazz);

        List<CamelEndpointDetails> details = new ArrayList<>();
        RouteBuilderParser.parseRouteBuilderEndpoints(clazz, ".",
                "src/test/java/org/apache/camel/parser/java/MySimpleToDRoute.java", details);
        LOG.info("{}", details);

        List<ParserResult> list = CamelJavaParserHelper.parseCamelConsumerUris(method, true, true);
        for (ParserResult result : list) {
            LOG.info("Consumer: " + result.getElement());
        }
        assertEquals("direct:start", list.get(0).getElement());

        list = CamelJavaParserHelper.parseCamelProducerUris(method, true, true);
        for (ParserResult result : list) {
            LOG.info("Producer: " + result.getElement());
        }
        assertEquals("toD", list.get(0).getNode());
        assertEquals("log:a", list.get(0).getElement());
        assertEquals("to", list.get(1).getNode());
        assertEquals("log:b", list.get(1).getElement());
        assertEquals("to", list.get(2).getNode());
        assertEquals("log:c", list.get(2).getElement());
        assertEquals(3, list.size());

        assertEquals(4, details.size());
        assertEquals("direct:start", details.get(0).getEndpointUri());
        assertEquals("log:a", details.get(1).getEndpointUri());
        assertEquals("log:b", details.get(2).getEndpointUri());
        assertEquals("log:c", details.get(3).getEndpointUri());
    }

};