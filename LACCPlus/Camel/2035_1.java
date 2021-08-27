//,temp,RouteBuilderTest.java,464,485,temp,RouteBuilderTest.java,351,378
//,3
public class xxx {
    @Test
    public void testThreads() throws Exception {

        List<Route> routes = buildThreads();

        log.debug("Created routes: " + routes);

        assertEquals(1, routes.size(), "Number routes created");
        for (Route route : routes) {
            Endpoint key = route.getEndpoint();
            assertEquals("direct://a", key.getEndpointUri(), "From endpoint");

            DefaultRoute consumer = assertIsInstanceOf(DefaultRoute.class, route);

            Pipeline line = assertIsInstanceOf(Pipeline.class, unwrap(consumer.getProcessor()));
            Iterator<Processor> it = line.next().iterator();

            assertIsInstanceOf(ThreadsProcessor.class, unwrapChannel(it.next()).getNextProcessor());
            assertIsInstanceOf(SendProcessor.class, unwrapChannel(it.next()).getNextProcessor());
            assertIsInstanceOf(SendProcessor.class, unwrapChannel(it.next()).getNextProcessor());
        }
    }

};