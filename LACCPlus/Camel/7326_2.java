//,temp,RoasterMyLocalAddRouteBuilderTest.java,38,58,temp,RoasterRouteBuilderEmptyUriTest.java,38,56
//,3
public class xxx {
    @Test
    void parse() throws Exception {
        JavaClassSource clazz = (JavaClassSource) Roaster
                .parse(new File("src/test/java/org/apache/camel/parser/java/MyRouteEmptyUriTest.java"));
        MethodSource<JavaClassSource> method = CamelJavaParserHelper.findConfigureMethod(clazz);

        List<ParserResult> list = CamelJavaParserHelper.parseCamelConsumerUris(method, true, false);
        for (ParserResult result : list) {
            LOG.info("Consumer: " + result.getElement());
        }
        assertEquals("direct:foo", list.get(0).getElement());

        list = CamelJavaParserHelper.parseCamelProducerUris(method, true, false);
        assertEquals(1, list.size());
        for (ParserResult result : list) {
            LOG.info("Producer: " + result.getElement());
            assertFalse(result.isParsed(), "Should be invalid");
        }
    }

};