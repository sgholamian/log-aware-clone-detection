//,temp,ManagedCamelContextDumpStatsAsXmlTest.java,32,55,temp,ManagedRouteDumpStatsAsXmlCustomDomainTest.java,34,57
//,3
public class xxx {
    @Test
    public void testPerformanceCounterStats() throws Exception {
        // get the stats for the route
        MBeanServer mbeanServer = getMBeanServer();
        ObjectName on = getContextObjectName();

        getMockEndpoint("mock:foo").expectedMessageCount(1);
        getMockEndpoint("mock:bar").expectedMessageCount(2);
        template.asyncSendBody("direct:start", "Hello World");
        template.asyncSendBody("direct:bar", "Hi World");
        template.asyncSendBody("direct:bar", "Bye World");
        assertMockEndpointsSatisfied();

        String xml = (String) mbeanServer.invoke(on, "dumpRoutesStatsAsXml", new Object[] { false, true },
                new String[] { "boolean", "boolean" });
        log.info(xml);

        // should be valid XML
        Document doc = context.getTypeConverter().convertTo(Document.class, xml);
        assertNotNull(doc);

        int processors = doc.getDocumentElement().getElementsByTagName("processorStat").getLength();
        assertEquals(5, processors);
    }

};