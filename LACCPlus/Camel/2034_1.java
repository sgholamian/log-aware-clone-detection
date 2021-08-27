//,temp,RouteBuilderTest.java,240,261,temp,RouteBuilderTest.java,108,127
//,3
public class xxx {
    @Test
    public void testWireTap() throws Exception {
        List<Route> routes = buildWireTap();

        log.debug("Created routes: " + routes);

        assertEquals(1, routes.size(), "Number routes created");
        for (Route route : routes) {
            Endpoint key = route.getEndpoint();
            assertEquals("direct://a", key.getEndpointUri(), "From endpoint");

            DefaultRoute consumer = assertIsInstanceOf(DefaultRoute.class, route);
            Channel channel = unwrapChannel(consumer.getProcessor());

            MulticastProcessor multicastProcessor = assertIsInstanceOf(MulticastProcessor.class, channel.getNextProcessor());
            List<Processor> endpoints = new ArrayList<>(multicastProcessor.getProcessors());
            assertEquals(2, endpoints.size(), "Should have 2 endpoints");

            assertSendToProcessor(unwrapChannel(endpoints.get(0)).getNextProcessor(), "direct://tap");
            assertSendToProcessor(unwrapChannel(endpoints.get(1)).getNextProcessor(), "direct://b");
        }
    }

};