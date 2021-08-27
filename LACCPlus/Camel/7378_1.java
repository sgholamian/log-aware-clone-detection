//,temp,ContextErrorHandlerTest.java,69,93,temp,RouteBuilderTest.java,303,323
//,3
public class xxx {
    @Test
    public void testOverloadingTheDefaultErrorHandler() throws Exception {

        RouteBuilder builder = new RouteBuilder() {
            public void configure() {
                errorHandler(deadLetterChannel("log:FOO.BAR"));
                from("seda:a").to("seda:b");
            }
        };

        List<Route> list = getRouteListWithCurrentContext(builder);
        assertEquals(1, list.size(), "Number routes created" + list);
        for (Route route : list) {
            Endpoint key = route.getEndpoint();
            assertEquals("seda://a", key.getEndpointUri(), "From endpoint");

            DefaultRoute consumerRoute = assertIsInstanceOf(DefaultRoute.class, route);
            Processor processor = consumerRoute.getProcessor();

            Channel channel = unwrapChannel(processor);
            assertIsInstanceOf(DeadLetterChannel.class, channel.getErrorHandler());
            SendProcessor sendProcessor = assertIsInstanceOf(SendProcessor.class, channel.getNextProcessor());
            log.debug("Found sendProcessor: " + sendProcessor);
        }
    }

};