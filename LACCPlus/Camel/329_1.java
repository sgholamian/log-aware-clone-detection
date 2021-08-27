//,temp,ManagedRouteAddRemoveTest.java,174,233,temp,ManagedRouteAddRemoveTest.java,113,172
//,2
public class xxx {
    @Test
    public void testRouteAddRemoteRouteWithRoutingSlip() throws Exception {
        MockEndpoint result = getMockEndpoint("mock:result");
        result.expectedMessageCount(1);
        template.sendBody("direct:start", "Hello World");
        result.assertIsSatisfied();

        MBeanServer mbeanServer = getMBeanServer();
        ObjectName on = getCamelObjectName(TYPE_SERVICE, "*");

        // number of SERVICES
        Set<ObjectName> names = mbeanServer.queryNames(on, null);
        assertEquals(SERVICES, names.size());

        // number of producers
        ObjectName onP = getCamelObjectName(TYPE_PRODUCER, "*");
        Set<ObjectName> namesP = mbeanServer.queryNames(onP, null);
        assertEquals(1, namesP.size());

        log.info("Adding 2nd route");

        // add a 2nd route
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:bar").routeId("bar").routingSlip(header("bar"));
            }
        });

        // and send a message to it
        MockEndpoint bar = getMockEndpoint("mock:bar");
        bar.expectedMessageCount(1);
        template.sendBodyAndHeader("direct:bar", "Hello World", "bar", "mock:bar");
        bar.assertIsSatisfied();

        // there should still be the same number of SERVICES
        names = mbeanServer.queryNames(on, null);
        assertEquals(SERVICES, names.size());

        // but as its recipient list which is dynamic-to we do not add a new producer
        namesP = mbeanServer.queryNames(onP, null);
        assertEquals(1, namesP.size());

        log.info("Removing 2nd route");

        // now remove the 2nd route
        context.getRouteController().stopRoute("bar");
        boolean removed = context.removeRoute("bar");
        assertTrue(removed);

        // there should still be the same number of SERVICES
        names = mbeanServer.queryNames(on, null);
        assertEquals(SERVICES, names.size());

        // and we still have the original producer
        namesP = mbeanServer.queryNames(onP, null);
        assertEquals(1, namesP.size());

        log.info("Shutting down...");
    }

};