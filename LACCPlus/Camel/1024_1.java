//,temp,ManagedCamelContextDumpRouteTemplatesAsXmlTest.java,30,48,temp,SpringJmxDumpCBRRoutesAsXmlTest.java,49,65
//,3
public class xxx {
    @Test
    public void testDumpAsXml() throws Exception {
        MBeanServer mbeanServer = getMBeanServer();

        ObjectName on = getContextObjectName();

        String xml = (String) mbeanServer.invoke(on, "dumpRouteTemplatesAsXml", null, null);
        assertNotNull(xml);
        log.info(xml);

        assertTrue(xml.contains("routeTemplate"));
        assertTrue(xml.contains("myTemplate"));
        assertTrue(xml.contains("<templateParameter name=\"foo\""));
        assertTrue(xml.contains("<templateParameter name=\"bar\""));
        assertTrue(xml.contains("direct:{{foo}}"));
        assertTrue(xml.contains("myOtherTemplate"));
        assertTrue(xml.contains("<templateParameter name=\"aaa\""));
        assertTrue(xml.contains("<header>{{aaa}}</header>"));
    }

};