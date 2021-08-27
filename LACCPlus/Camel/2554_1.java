//,temp,ManagedProducerRouteAddRemoveRegisterAlwaysTest.java,45,104,temp,ManagedRouteAddRemoveTest.java,412,469
//,3
public class xxx {
    @Test
    public void testRouteAddRemoteRouteWithRecipientList() throws Exception {
        MockEndpoint result = getMockEndpoint("mock:result");
        result.expectedMessageCount(1);
        template.sendBody("direct:start", "Hello World");
        result.assertIsSatisfied();

        MBeanServer mbeanServer = getMBeanServer();
        ObjectName on = getCamelObjectName(TYPE_SERVICE, "*");

        // number of services
        Set<ObjectName> names = mbeanServer.queryNames(on, null);
        assertEquals(SERVICES, names.size());

        // number of producers
        ObjectName onP = getCamelObjectName(TYPE_PRODUCER, "*");
        Set<ObjectName> namesP = mbeanServer.queryNames(onP, null);
        assertEquals(3, namesP.size());

        log.info("Adding 2nd route");

        // add a 2nd route
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:bar").routeId("bar").recipientList(header("bar"));
            }
        });

        // and send a message to it
        MockEndpoint bar = getMockEndpoint("mock:bar");
        bar.expectedMessageCount(1);
        template.sendBodyAndHeader("direct:bar", "Hello World", "bar", "mock:bar");
        bar.assertIsSatisfied();

        // there should still be the same number of services
        names = mbeanServer.queryNames(on, null);
        assertEquals(SERVICES, names.size());

        // but as its recipient list which is dynamic-to we add new producers because we have register always
        namesP = mbeanServer.queryNames(onP, null);
        assertEquals(5, namesP.size());

        log.info("Removing 2nd route");

        // now remove the 2nd route
        context.getRouteController().stopRoute("bar");
        boolean removed = context.removeRoute("bar");
        assertTrue(removed);

        // there should still be the same number of services
        names = mbeanServer.queryNames(on, null);
        assertEquals(SERVICES, names.size());

        // and we still have the other producers, but not the one from the 2nd route that was removed
        namesP = mbeanServer.queryNames(onP, null);
        assertEquals(4, namesP.size());

        log.info("Shutting down...");
    }

};