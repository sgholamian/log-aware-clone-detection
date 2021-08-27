//,temp,ManagedRouteDumpRouteAsXmlTest.java,36,61,temp,ManagedRouteDumpRouteAsXmlPlaceholderTest.java,42,66
//,3
public class xxx {
    @Test
    public void testDumpAsXml() throws Exception {
        MBeanServer mbeanServer = getMBeanServer();
        ObjectName on = getRouteObjectName(mbeanServer);

        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedBodiesReceived("Hello World");

        template.sendBody("direct:start", "Hello World");

        assertMockEndpointsSatisfied();

        // should be started
        String routeId = (String) mbeanServer.getAttribute(on, "RouteId");
        assertEquals("myRoute", routeId);

        String xml = (String) mbeanServer.invoke(on, "dumpRouteAsXml", new Object[] { true }, new String[] { "boolean" });
        assertNotNull(xml);
        log.info(xml);

        assertTrue(xml.contains("direct:start"));
        assertTrue(xml.contains("route"));
        assertTrue(xml.contains("myRoute"));
        assertTrue(xml.contains("mock:result"));
    }

};