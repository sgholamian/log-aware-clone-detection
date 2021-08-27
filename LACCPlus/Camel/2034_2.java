//,temp,RouteBuilderTest.java,240,261,temp,RouteBuilderTest.java,108,127
//,3
public class xxx {
    @Test
    public void testSimpleRouteWithHeaderPredicate() throws Exception {
        List<Route> routes = buildSimpleRouteWithHeaderPredicate();

        log.debug("Created routes: " + routes);

        assertEquals(1, routes.size(), "Number routes created");
        for (Route route : routes) {
            Endpoint key = route.getEndpoint();
            assertEquals("direct://a", key.getEndpointUri(), "From endpoint");

            DefaultRoute consumer = assertIsInstanceOf(DefaultRoute.class, route);
            Channel channel = unwrapChannel(consumer.getProcessor());

            FilterProcessor filterProcessor = assertIsInstanceOf(FilterProcessor.class, channel.getNextProcessor());
            SendProcessor sendProcessor
                    = assertIsInstanceOf(SendProcessor.class, unwrapChannel(filterProcessor).getNextProcessor());
            assertEquals("direct://b", sendProcessor.getDestination().getEndpointUri(), "Endpoint URI");
        }
    }

};