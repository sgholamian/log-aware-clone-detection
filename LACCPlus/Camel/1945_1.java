//,temp,RoasterRouteBuilderCamelTestSupportTest.java,37,54,temp,RoasterConcatFieldRouteBuilderConfigureTest.java,37,54
//,3
public class xxx {
    @Test
    void parse() throws Exception {
        JavaClassSource clazz
                = (JavaClassSource) Roaster.parse(new File("src/test/java/org/apache/camel/parser/java/MyRouteTest.java"));
        MethodSource<JavaClassSource> method = CamelJavaParserHelper.findConfigureMethod(clazz);

        List<ParserResult> list = CamelJavaParserHelper.parseCamelConsumerUris(method, true, false);
        for (ParserResult result : list) {
            LOG.info("Consumer: " + result.getElement());
        }
        assertEquals("direct:foo", list.get(0).getElement());

        list = CamelJavaParserHelper.parseCamelProducerUris(method, true, false);
        for (ParserResult result : list) {
            LOG.info("Producer: " + result.getElement());
        }
        assertEquals("mock:foo", list.get(0).getElement());
    }

};