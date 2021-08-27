//,temp,RoasterRouteDuplicateIdBuilderDCamelTestSupportTest.java,37,57,temp,RoasterMyNettyTest.java,37,57
//,3
public class xxx {
    @Test
    void parse() throws Exception {
        JavaClassSource clazz = (JavaClassSource) Roaster
                .parse(new File("src/test/java/org/apache/camel/parser/java/MyRouteDuplicateIdTest.java"));
        MethodSource<JavaClassSource> method = CamelJavaParserHelper.findConfigureMethod(clazz);

        List<ParserResult> list = CamelJavaParserHelper.parseCamelConsumerUris(method, true, false);
        for (ParserResult result : list) {
            LOG.info("Consumer: " + result.getElement());
        }
        assertEquals(3, list.size());

        list = CamelJavaParserHelper.parseCamelRouteIds(method);
        for (ParserResult result : list) {
            LOG.info("Route id: " + result.getElement());
        }
        assertEquals(3, list.size());
        assertEquals("foo", list.get(0).getElement());
        assertEquals("bar", list.get(1).getElement());
        assertEquals("foo", list.get(2).getElement());
    }

};