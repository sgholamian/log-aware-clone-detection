//,temp,RoasterSimpleToFTest.java,40,68,temp,RoasterSimpleProcessorTest.java,40,65
//,3
public class xxx {
    @Test
    void parse() throws Exception {
        JavaClassSource clazz = (JavaClassSource) Roaster
                .parse(new File("src/test/java/org/apache/camel/parser/java/SimpleProcessorTest.java"));
        MethodSource<JavaClassSource> method = CamelJavaParserHelper.findConfigureMethod(clazz);

        List<CamelEndpointDetails> details = new ArrayList<>();
        RouteBuilderParser.parseRouteBuilderEndpoints(clazz, ".",
                "src/test/java/org/apache/camel/parser/java/SimpleProcessorTest.java", details);
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
        assertEquals(0, list.size());

        assertEquals(1, details.size());
        assertEquals("direct:start", details.get(0).getEndpointUri());
    }

};