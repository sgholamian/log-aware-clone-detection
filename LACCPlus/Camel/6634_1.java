//,temp,BacklogTracerTest.java,211,244,temp,BacklogTracerTest.java,91,128
//,3
public class xxx {
    @Test
    public void testBacklogTracerEventMessageDumpAllAsXml() throws Exception {
        MBeanServer mbeanServer = getMBeanServer();
        ObjectName on
                = new ObjectName("org.apache.camel:context=" + context.getManagementName() + ",type=tracer,name=BacklogTracer");
        assertNotNull(on);
        mbeanServer.isRegistered(on);

        Boolean enabled = (Boolean) mbeanServer.getAttribute(on, "Enabled");
        assertEquals(Boolean.FALSE, enabled, "Should not be enabled");

        // enable it
        mbeanServer.setAttribute(on, new Attribute("Enabled", Boolean.TRUE));

        getMockEndpoint("mock:foo").expectedMessageCount(2);
        getMockEndpoint("mock:bar").expectedMessageCount(2);

        template.sendBody("direct:start", "Hello World");
        template.sendBody("direct:start", "Bye World");

        assertMockEndpointsSatisfied();

        String events = (String) mbeanServer.invoke(on, "dumpAllTracedMessagesAsXml", null, null);

        assertNotNull(events);
        log.info(events);

        // should be valid XML
        Document dom = context.getTypeConverter().convertTo(Document.class, events);
        assertNotNull(dom);

        NodeList list = dom.getElementsByTagName("backlogTracerEventMessage");
        assertEquals(6, list.getLength());
    }

};