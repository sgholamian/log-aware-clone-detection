//,temp,RouteBuilderTest.java,393,409,temp,RouteBuilderTest.java,279,301
//,3
public class xxx {
    @Test
    public void testSplitter() throws Exception {

        List<Route> routes = buildSplitter();

        log.debug("Created routes: " + routes);

        assertEquals(1, routes.size(), "Number routes created");
        for (Route route : routes) {
            Endpoint key = route.getEndpoint();
            assertEquals("direct://a", key.getEndpointUri(), "From endpoint");

            DefaultRoute consumer = assertIsInstanceOf(DefaultRoute.class, route);
            Channel channel = unwrapChannel(consumer.getProcessor());
            assertIsInstanceOf(Splitter.class, channel.getNextProcessor());
        }
    }

};