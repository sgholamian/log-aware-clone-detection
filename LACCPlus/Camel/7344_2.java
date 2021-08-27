//,temp,ManagedRouteDumpRouteAsXmlTest.java,90,116,temp,ManagedRouteDumpRouteAsXmlTest.java,63,88
//,3
public class xxx {
    @Test
    public void testDumpAsXmlResolvePlaceholder() throws Exception {
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

        assertTrue(xml.contains("route"));
        assertTrue(xml.contains("myRoute"));
        assertTrue(xml.contains("ref:bar"));
        assertTrue(xml.contains("mock:result"));
        assertTrue(xml.contains("java.lang.Exception"));
    }

};