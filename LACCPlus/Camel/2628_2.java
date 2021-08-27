//,temp,BacklogDebuggerTest.java,176,252,temp,BacklogDebuggerTest.java,38,96
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    @Test
    public void testBacklogDebugger() throws Exception {
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
        mock.expectedMessageCount(0);
        mock.setSleepForEmptyTest(100);

        template.sendBody("seda:start", "Hello World");

        assertMockEndpointsSatisfied();

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

        assertTrue(xml.contains("Hello World"), "Should contain our body");
        assertTrue(xml.contains("<toNode>bar</toNode>"), "Should contain bar node");

        resetMocks();
        mock.expectedMessageCount(1);

        // resume breakpoint
        mbeanServer.invoke(on, "resumeBreakpoint", new Object[] { "bar" }, new String[] { "java.lang.String" });

        assertMockEndpointsSatisfied();

        // and no suspended anymore
        Set<String> nodes = (Set<String>) mbeanServer.invoke(on, "getSuspendedBreakpointNodeIds", null, null);
        assertNotNull(nodes);
        assertEquals(0, nodes.size());
    }

};