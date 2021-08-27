//,temp,RouteBuilderTest.java,393,409,temp,RouteBuilderTest.java,279,301
//,3
public class xxx {
    @Test
    public void testRouteWithInterceptor() throws Exception {

        List<Route> routes = buildRouteWithInterceptor();

        log.debug("Created routes: " + routes);

        assertEquals(1, routes.size(), "Number routes created");
        for (Route route : routes) {
            Endpoint key = route.getEndpoint();
            assertEquals("direct://a", key.getEndpointUri(), "From endpoint");

            DefaultRoute consumer = assertIsInstanceOf(DefaultRoute.class, route);

            Pipeline line = assertIsInstanceOf(Pipeline.class, unwrap(consumer.getProcessor()));
            assertEquals(3, line.next().size());
            // last should be our seda

            List<Processor> processors = new ArrayList<>(line.next());
            Processor sendTo = assertIsInstanceOf(SendProcessor.class, unwrapChannel(processors.get(2)).getNextProcessor());
            assertSendTo(sendTo, "direct://d");
        }
    }

};