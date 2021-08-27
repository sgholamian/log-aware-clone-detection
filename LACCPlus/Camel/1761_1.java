//,temp,RouteSedaStopStartTest.java,30,68,temp,TwoRouteSuspendResumeTest.java,32,77
//,3
public class xxx {
    @Test
    public void testStopStart() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedBodiesReceived("A");

        template.sendBody("seda:foo", "A");

        assertMockEndpointsSatisfied();

        log.info("Stopping");

        // now suspend and dont expect a message to be routed
        resetMocks();
        mock.expectedMessageCount(0);
        context.getRouteController().stopRoute("foo");

        assertEquals("Stopped", context.getRouteController().getRouteStatus("foo").name());
        Route route = context.getRoute("foo");
        if (route instanceof StatefulService) {
            assertEquals("Stopped", ((StatefulService) route).getStatus().name());
        }

        template.sendBody("seda:foo", "B");
        mock.assertIsSatisfied(1000);

        log.info("Starting");

        // now resume and expect the previous message to be routed
        resetMocks();
        mock.expectedBodiesReceived("B");
        context.getRouteController().startRoute("foo");
        assertMockEndpointsSatisfied();

        assertEquals("Started", context.getRouteController().getRouteStatus("foo").name());
        route = context.getRoute("foo");
        if (route instanceof StatefulService) {
            assertEquals("Started", ((StatefulService) route).getStatus().name());
        }
    }

};