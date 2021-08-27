//,temp,RoasterSplitTokenizeTest.java,40,76,temp,RoasterSimpleToDTest.java,40,74
//,3
public class xxx {
    @Test
    void parse() throws Exception {
        JavaClassSource clazz = (JavaClassSource) Roaster
                .parse(new File("src/test/java/org/apache/camel/parser/java/SplitTokenizeTest.java"));
        MethodSource<JavaClassSource> method = CamelJavaParserHelper.findConfigureMethod(clazz);

        List<CamelEndpointDetails> details = new ArrayList<>();
        RouteBuilderParser.parseRouteBuilderEndpoints(clazz, "src/test/java", "org/apache/camel/parser/SplitTokenizeTest.java",
                details);
        LOG.info("{}", details);

        List<ParserResult> list = CamelJavaParserHelper.parseCamelConsumerUris(method, true, true);
        for (ParserResult result : list) {
            LOG.info("Consumer: " + result.getElement());
        }
        assertEquals("direct:a", list.get(0).getElement());
        assertEquals("direct:b", list.get(1).getElement());
        assertEquals("direct:c", list.get(2).getElement());
        assertEquals("direct:d", list.get(3).getElement());
        assertEquals("direct:e", list.get(4).getElement());
        assertEquals("direct:f", list.get(5).getElement());

        list = CamelJavaParserHelper.parseCamelProducerUris(method, true, true);
        for (ParserResult result : list) {
            LOG.info("Producer: " + result.getElement());
        }
        assertEquals("mock:split", list.get(0).getElement());
        assertEquals("mock:split", list.get(1).getElement());
        assertEquals("mock:split", list.get(2).getElement());
        assertEquals("mock:split", list.get(3).getElement());
        assertEquals("mock:split", list.get(4).getElement());
        assertEquals("mock:split", list.get(5).getElement());

        assertEquals(12, details.size());
        assertEquals("direct:a", details.get(0).getEndpointUri());
        assertEquals("mock:split", details.get(11).getEndpointUri());
    }

};