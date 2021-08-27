//,temp,RouteBuilderTest.java,464,485,temp,RouteBuilderTest.java,351,378
//,3
public class xxx {
    @Test
    public void testRouteDynamicReceipentList() throws Exception {

        List<Route> routes = buildDynamicRecipientList();

        log.debug("Created routes: " + routes);

        assertEquals(1, routes.size(), "Number routes created");
        for (Route route : routes) {
            Endpoint key = route.getEndpoint();
            assertEquals("direct://a", key.getEndpointUri(), "From endpoint");

            DefaultRoute consumer = assertIsInstanceOf(DefaultRoute.class, route);
            Channel channel = unwrapChannel(consumer.getProcessor());

            Pipeline line = assertIsInstanceOf(Pipeline.class, channel.getNextProcessor());
            Iterator<?> it = line.next().iterator();

            // EvaluateExpressionProcessor should be wrapped in error handler
            Object first = it.next();
            first = assertIsInstanceOf(DeadLetterChannel.class, first).getOutput();
            assertIsInstanceOf(EvaluateExpressionProcessor.class, first);

            // and the second should NOT be wrapped in error handler
            Object second = it.next();
            assertIsInstanceOf(RecipientList.class, second);
        }
    }

};