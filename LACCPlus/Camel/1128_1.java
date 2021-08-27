//,temp,ManagedCamelContextDumpRoutesAsXmlTest.java,33,51,temp,SpringJmxDumpRoutesAsXmlTest.java,49,65
//,3
public class xxx {
    @Test
    public void testDumpAsXml() throws Exception {
        MBeanServer mbeanServer = getMBeanServer();

        ObjectName on = getContextObjectName();

        String xml = (String) mbeanServer.invoke(on, "dumpRoutesAsXml", null, null);
        assertNotNull(xml);
        log.info(xml);

        assertTrue(xml.contains("route"));
        assertTrue(xml.contains("myRoute"));
        assertTrue(xml.contains("myOtherRoute"));
        assertTrue(xml.contains("direct:start"));
        assertTrue(xml.contains("{{result}}"));
        assertTrue(xml.contains("seda:bar"));
        assertTrue(xml.contains("ref:bar"));
        assertTrue(xml.contains("<header>bar</header>"));
    }

};