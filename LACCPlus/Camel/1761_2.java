//,temp,RouteSedaStopStartTest.java,30,68,temp,TwoRouteSuspendResumeTest.java,32,77
//,3
public class xxx {
    @Test
    public void testSuspendResume() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedBodiesReceived("A");

        template.sendBody("seda:foo", "A");

        assertMockEndpointsSatisfied();

        log.info("Suspending");

        // now suspend and dont expect a message to be routed
        resetMocks();
        mock.expectedMessageCount(0);

        MockEndpoint mockBar = getMockEndpoint("mock:bar");
        mockBar.expectedMessageCount(1);

        context.getRouteController().suspendRoute("foo");

        // need to give seda consumer thread time to idle
        await().atMost(1, TimeUnit.SECONDS).until(() -> {
            return context.getEndpoint("seda:bar", SedaEndpoint.class).getQueue().size() == 0;
        });

        template.sendBody("seda:foo", "B");
        template.sendBody("direct:bar", "C");

        // we can still send a message to bar when foo route is suspended
        mockBar.assertIsSatisfied();
        mock.assertIsSatisfied(1000);

        assertEquals("Suspended", context.getRouteController().getRouteStatus("foo").name());
        assertEquals("Started", context.getRouteController().getRouteStatus("bar").name());

        log.info("Resuming");

        // now resume and expect the previous message to be routed
        resetMocks();
        mock.expectedBodiesReceived("B");
        context.getRouteController().resumeRoute("foo");
        assertMockEndpointsSatisfied();

        assertEquals("Started", context.getRouteController().getRouteStatus("foo").name());
        assertEquals("Started", context.getRouteController().getRouteStatus("bar").name());
    }

};