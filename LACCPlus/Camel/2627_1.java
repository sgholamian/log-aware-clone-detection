//,temp,BacklogDebuggerTest.java,254,328,temp,BacklogDebuggerTest.java,98,174
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    @Test
    public void testBacklogDebuggerRemoveBodyAndHeader() throws Exception {
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
        mbeanServer.invoke(on, "addBreakpoint", new Object[] { "foo" }, new String[] { "java.lang.String" });
        mbeanServer.invoke(on, "addBreakpoint", new Object[] { "bar" }, new String[] { "java.lang.String" });

        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMessageCount(0);
        mock.setSleepForEmptyTest(100);

        template.sendBody("seda:start", "Hello World");

        assertMockEndpointsSatisfied();

        // add breakpoint at bar
        Set<String> nodes = (Set<String>) mbeanServer.invoke(on, "getSuspendedBreakpointNodeIds", null, null);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        assertEquals("foo", nodes.iterator().next());

        // update body and header
        mbeanServer.invoke(on, "removeMessageBodyOnBreakpoint", new Object[] { "foo" }, new String[] { "java.lang.String" });
        mbeanServer.invoke(on, "removeMessageHeaderOnBreakpoint", new Object[] { "foo", "beer" },
                new String[] { "java.lang.String", "java.lang.String" });

        // resume breakpoint
        mbeanServer.invoke(on, "resumeBreakpoint", new Object[] { "foo" }, new String[] { "java.lang.String" });

        // wait for breakpoint at bar
        await().atMost(1, TimeUnit.SECONDS).untilAsserted(() -> {
            Set<String> suspended = (Set<String>) mbeanServer.invoke(on, "getSuspendedBreakpointNodeIds", null, null);
            assertNotNull(suspended);
            assertEquals(1, suspended.size());
            assertEquals("bar", suspended.iterator().next());
        });

        // the message should be ours
        String xml = (String) mbeanServer.invoke(on, "dumpTracedMessagesAsXml", new Object[] { "bar" },
                new String[] { "java.lang.String" });
        assertNotNull(xml);
        log.info(xml);

        assertTrue(xml.contains("<body>[Body is null]</body>"), "Should not contain our body");
        assertTrue(xml.contains("<toNode>bar</toNode>"), "Should contain bar node");
        assertFalse(xml.contains("<header"), "Should not contain any headers");

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