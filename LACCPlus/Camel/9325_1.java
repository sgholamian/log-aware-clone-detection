//,temp,ManagedFromRestGetEmbeddedRouteTest.java,40,68,temp,SpringJmxDumpRoutesAsXmlIncludeHostNameTest.java,50,68
//,3
public class xxx {
    @Test
    public void testFromRestModel() throws Exception {
        MBeanServer mbeanServer = getMBeanServer();

        ObjectName on = getContextObjectName();

        String xml = (String) mbeanServer.invoke(on, "dumpRestsAsXml", null, null);
        assertNotNull(xml);
        log.info(xml);

        assertTrue(xml.contains("<rests"));
        assertTrue(xml.contains("<rest path=\"/say/hello\">"));
        assertTrue(xml.contains("<rest path=\"/say/bye\">"));
        assertTrue(xml.contains("</rest>"));
        assertTrue(xml.contains("<get"));
        assertTrue(xml.contains("<route"));
        assertTrue(xml.contains("<transform"));
        assertTrue(xml.contains("<post"));
        assertTrue(xml.contains("application/json"));
        assertTrue(xml.contains("</rests>"));

        String xml2 = (String) mbeanServer.invoke(on, "dumpRoutesAsXml", null, null);
        log.info(xml2);
        // and we should have rest in the routes that indicate its from a rest dsl
        assertTrue(xml2.contains("rest=\"true\""));

        // there should be 2 + 1 routes
        assertEquals(2 + 1, context.getRouteDefinitions().size());
    }

};