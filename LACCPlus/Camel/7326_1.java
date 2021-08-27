//,temp,RoasterMyLocalAddRouteBuilderTest.java,38,58,temp,RoasterRouteBuilderEmptyUriTest.java,38,56
//,3
public class xxx {
    @Test
    void parse() throws Exception {
        JavaClassSource clazz = (JavaClassSource) Roaster
                .parse(new File("src/test/java/org/apache/camel/parser/java/MyLocalAddRouteBuilderTest.java"));
        MethodSource<JavaClassSource> method = CamelJavaParserHelper.findConfigureMethod(clazz);
        assertNull(method);

        List<MethodSource<JavaClassSource>> methods = CamelJavaParserHelper.findInlinedConfigureMethods(clazz);
        assertEquals(1, methods.size());
        method = methods.get(0);

        List<ParserResult> list = CamelJavaParserHelper.parseCamelConsumerUris(method, true, true);
        for (ParserResult result : list) {
            LOG.info("Consumer: " + result.getElement());
        }

        list = CamelJavaParserHelper.parseCamelProducerUris(method, true, true);
        for (ParserResult result : list) {
            LOG.info("Producer: " + result.getElement());
        }
    }

};