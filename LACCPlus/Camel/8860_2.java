//,temp,ManagedRouteAddSecondRouteNotRegisterNewRoutesTest.java,49,73,temp,ManagedRouteAddSecondRouteTest.java,44,69
//,3
public class xxx {
    @Test
    public void testRouteAddSecondRoute() throws Exception {
        MBeanServer mbeanServer = getMBeanServer();
        ObjectName route1 = getCamelObjectName(TYPE_ROUTE, "foo");

        // should be started
        String state = (String) mbeanServer.getAttribute(route1, "State");
        assertEquals(ServiceStatus.Started.name(), state, "Should be started");

        log.info(">>>>>>>>>>>>>>>>> adding 2nd route <<<<<<<<<<<<<<");
        // add a 2nd route
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:bar").routeId("bar").to("mock:bar");
            }
        });
        log.info(">>>>>>>>>>>>>>>>> adding 2nd route DONE <<<<<<<<<<<<<<");

        // find the 2nd route
        ObjectName route2 = getCamelObjectName(TYPE_ROUTE, "bar");

        // should be started
        state = (String) mbeanServer.getAttribute(route2, "State");
        assertEquals(ServiceStatus.Started.name(), state, "Should be started");
    }

};