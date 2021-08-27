//,temp,ManagedRouteAddRemoveTest.java,354,410,temp,ManagedRouteAddRemoveTest.java,294,352
//,3
public class xxx {
    @Test
    public void testRouteAddRemoteRouteWithRecipientListAndRouteScopedOnCompletion() throws Exception {
        MockEndpoint result = getMockEndpoint("mock:result");
        result.expectedMessageCount(1);
        template.sendBody("direct:start", "Hello World");
        result.assertIsSatisfied();

        MBeanServer mbeanServer = getMBeanServer();
        ObjectName on = getCamelObjectName(TYPE_SERVICE, "*");

        // number of SERVICES
        Set<ObjectName> names = mbeanServer.queryNames(on, null);
        assertEquals(SERVICES, names.size());

        log.info("Adding 2nd route");

        // add a 2nd route
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:bar").routeId("bar")
                        .onCompletion()
                        .recipientList(header("done"))
                        .end().end()
                        .recipientList(header("bar"));
            }
        });

        // and send a message to it
        getMockEndpoint("mock:bar").expectedMessageCount(1);
        getMockEndpoint("mock:done").expectedMessageCount(1);

        Map<String, Object> headers = new HashMap<>();
        headers.put("done", "mock:done");
        headers.put("bar", "mock:bar");
        template.sendBodyAndHeaders("direct:bar", "Hello World", headers);

        assertMockEndpointsSatisfied();

        // there should still be the same number of SERVICES
        names = mbeanServer.queryNames(on, null);
        assertEquals(SERVICES, names.size());

        // now stop and remove the 2nd route
        log.info("Stopping 2nd route");
        context.getRouteController().stopRoute("bar");

        log.info("Removing 2nd route");
        boolean removed = context.removeRoute("bar");
        assertTrue(removed);

        // there should still be the same number of SERVICES
        names = mbeanServer.queryNames(on, null);
        assertEquals(SERVICES, names.size());

        log.info("Shutting down...");
    }

};