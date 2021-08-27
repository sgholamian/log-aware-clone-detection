//,temp,ManagedCamelContextDumpRouteTemplatesAsXmlTest.java,30,48,temp,SpringJmxDumpCBRRoutesAsXmlTest.java,49,65
//,3
public class xxx {
    @Test
    public void testJmxDumpCBRRoutesAsXml() throws Exception {
        MBeanServer mbeanServer = getMBeanServer();

        ObjectName on = getContextObjectName();
        String xml = (String) mbeanServer.invoke(on, "dumpRoutesAsXml", null, null);
        assertNotNull(xml);
        log.info(xml);

        assertTrue(xml.contains("myRoute"), xml);
        assertTrue(xml.matches("[\\S\\s]*<when id=\"when[0-9]+\">[\\S\\s]*"));
        assertTrue(xml.matches("[\\S\\s]*<otherwise id=\"otherwise[0-9]+\">[\\S\\s]*"));
        assertTrue(xml.contains("<route customId=\"true\" id=\"myRoute\">")
                || xml.contains("<route id=\"myRoute\" customId=\"true\">"));
        assertTrue(xml.contains("<choice customId=\"true\" id=\"myChoice\">")
                || xml.contains("<choice id=\"myChoice\" customId=\"true\">"));
    }

};