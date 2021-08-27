//,temp,ManagedRouteRestartTest.java,38,84,temp,ManagedRouteStopTest.java,34,80
//,3
public class xxx {
    @Test
    public void testRestartRoute() throws Exception {
        assertEquals(1, myRoutePolicy.getStart());
        assertEquals(0, myRoutePolicy.getStop());

        // fire a message to get it running
        getMockEndpoint("mock:result").expectedMessageCount(1);
        template.sendBody("direct:start", "Hello World");
        assertMockEndpointsSatisfied();

        MBeanServer mbeanServer = getMBeanServer();

        Set<ObjectName> set = mbeanServer.queryNames(new ObjectName("*:type=routes,*"), null);
        assertEquals(1, set.size());

        ObjectName on = set.iterator().next();

        boolean registered = mbeanServer.isRegistered(on);
        assertEquals(true, registered, "Should be registered");

        String uri = (String) mbeanServer.getAttribute(on, "EndpointUri");
        // the route has this starting endpoint uri
        assertEquals("direct://start", uri);

        // should be started
        String state = (String) mbeanServer.getAttribute(on, "State");
        assertEquals(ServiceStatus.Started.name(), state, "Should be started");

        String uptime = (String) mbeanServer.getAttribute(on, "Uptime");
        assertNotNull(uptime);
        log.info("Uptime: {}", uptime);

        long uptimeMillis = (Long) mbeanServer.getAttribute(on, "UptimeMillis");
        assertTrue(uptimeMillis > 0);

        mbeanServer.invoke(on, "restart", null, null);

        registered = mbeanServer.isRegistered(on);
        assertEquals(true, registered, "Should be registered");

        // should be started
        state = (String) mbeanServer.getAttribute(on, "State");
        assertEquals(ServiceStatus.Started.name(), state, "Should be started");

        assertEquals(2, myRoutePolicy.getStart());
        assertEquals(1, myRoutePolicy.getStop());
    }

};