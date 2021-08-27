//,temp,ManagedCamelContextDumpRoutesAsXmlTest.java,73,93,temp,ManagedCamelContextDumpRoutesAsXmlTest.java,53,71
//,3
public class xxx {
    @Test
    public void testDumpAsXmlResolvePlaceholder() throws Exception {
        MBeanServer mbeanServer = getMBeanServer();

        ObjectName on = getContextObjectName();

        String xml = (String) mbeanServer.invoke(on, "dumpRoutesAsXml", new Object[] { true }, new String[] { "boolean" });
        assertNotNull(xml);
        log.info(xml);

        assertTrue(xml.contains("route"));
        assertTrue(xml.contains("myRoute"));
        assertTrue(xml.contains("myOtherRoute"));
        assertTrue(xml.contains("direct:start"));
        assertTrue(xml.contains("mock:result"));
        assertTrue(xml.contains("seda:bar"));
        assertTrue(xml.contains("ref:bar"));
        assertTrue(xml.contains("<header>bar</header>"));
    }

};