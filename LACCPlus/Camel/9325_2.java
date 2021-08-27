//,temp,ManagedFromRestGetEmbeddedRouteTest.java,40,68,temp,SpringJmxDumpRoutesAsXmlIncludeHostNameTest.java,50,68
//,3
public class xxx {
    @Test
    public void testJmxDumpRoutesAsXml() throws Exception {
        MBeanServer mbeanServer = getMBeanServer();

        ObjectName on = ObjectName
                .getInstance("org.apache.camel:context=localhost/" + context.getManagementName() + ",type=context,name=\""
                             + context.getName() + "\"");
        String xml = (String) mbeanServer.invoke(on, "dumpRoutesAsXml", null, null);
        assertNotNull(xml);
        log.info(xml);

        assertTrue(xml.contains("route"));
        assertTrue(xml.contains("myRoute"));
        assertTrue(xml.contains("myOtherRoute"));
        assertTrue(xml.contains("direct:start"));
        assertTrue(xml.contains("mock:result"));
        assertTrue(xml.contains("seda:bar"));
        assertTrue(xml.contains("mock:bar"));
    }

};