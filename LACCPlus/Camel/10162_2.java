//,temp,BacklogDebuggerTest.java,389,474,temp,BacklogDebuggerTest.java,330,387
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    @Test
    public void testBacklogDebuggerSuspendOnlyOneAtBreakpoint() throws Exception {
        MBeanServer mbeanServer = getMBeanServer();
        ObjectName on = new ObjectName(
                "org.apache.camel:context=" + context.getManagementName() + ",type=tracer,name=BacklogDebugger");
        assertNotNull(on);
        mbeanServer.isRegistered(on);

        Boolean enabled = (Boolean) mbeanServer.getAttribute(on, "Enabled");
        assertEquals(Boolean.FALSE, enabled, "Should not be enabled");

        // enable debugger
        mbeanServer.invoke(on, "enableDebugger", null, null);

        enabled = (Boolean) mbeanServer.getAttribute(on, "Enabled");
        assertEquals(Boolean.TRUE, enabled, "Should be enabled");

        // add breakpoint at bar
        mbeanServer.invoke(on, "addBreakpoint", new Object[] { "bar" }, new String[] { "java.lang.String" });

        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMessageCount(2);

        // only one of them is suspended
        template.sendBody("seda:start", "Hello World");
        template.sendBody("seda:start", "Hello Camel");
        template.sendBody("seda:start", "Hello Earth");

        assertMockEndpointsSatisfied();

        // add breakpoint at bar
        Set<String> nodes = (Set<String>) mbeanServer.invoke(on, "getSuspendedBreakpointNodeIds", null, null);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        assertEquals("bar", nodes.iterator().next());

        // the message should be ours
        String xml = (String) mbeanServer.invoke(on, "dumpTracedMessagesAsXml", new Object[] { "bar" },
                new String[] { "java.lang.String" });
        assertNotNull(xml);
        log.info(xml);

        assertTrue(xml.contains("<toNode>bar</toNode>"), "Should contain bar node");

        resetMocks();
        mock.expectedMessageCount(1);

        // resume breakpoint
        mbeanServer.invoke(on, "resumeBreakpoint", new Object[] { "bar" }, new String[] { "java.lang.String" });

        assertMockEndpointsSatisfied();

        // and no suspended anymore
        nodes = (Set<String>) mbeanServer.invoke(on, "getSuspendedBreakpointNodeIds", null, null);
        assertNotNull(nodes);
        assertEquals(0, nodes.size());
    }

};