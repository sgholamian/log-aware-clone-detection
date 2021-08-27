//,temp,RoasterRouteDuplicateIdBuilderDCamelTestSupportTest.java,37,57,temp,RoasterMyNettyTest.java,37,57
//,3
public class xxx {
    @Test
    void parse() throws Exception {
        JavaClassSource clazz
                = (JavaClassSource) Roaster.parse(new File("src/test/java/org/apache/camel/parser/java/MyNettyTest.java"));
        MethodSource<JavaClassSource> method = CamelJavaParserHelper.findConfigureMethod(clazz);

        List<ParserResult> list = CamelJavaParserHelper.parseCamelConsumerUris(method, true, true);
        for (ParserResult result : list) {
            LOG.info("Consumer: " + result.getElement());
        }
        assertEquals("netty-http:http://0.0.0.0:{{port}}/foo", list.get(0).getElement());
        assertEquals("netty-http:http://0.0.0.0:{{getNextPort}}/bar", list.get(1).getElement());

        list = CamelJavaParserHelper.parseCamelProducerUris(method, true, true);
        for (ParserResult result : list) {
            LOG.info("Producer: " + result.getElement());
        }
        assertEquals("mock:input1", list.get(0).getElement());
        assertEquals("netty-http:http://0.0.0.0:{{getNextPort}}/bar", list.get(1).getElement());
        assertEquals("mock:input2", list.get(2).getElement());
    }

};