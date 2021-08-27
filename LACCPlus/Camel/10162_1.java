//,temp,BacklogDebuggerTest.java,389,474,temp,BacklogDebuggerTest.java,330,387
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    @Test
    public void testBacklogDebuggerConditional() throws Exception {
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

        // validate conditional breakpoint (mistake on purpose)
        Object out = mbeanServer.invoke(on, "validateConditionalBreakpoint",
                new Object[] { "unknown", "${body contains 'Camel'" }, new String[] { "java.lang.String", "java.lang.String" });
        assertEquals("No language could be found for: unknown", out);

        // validate conditional breakpoint (mistake on purpose)
        out = mbeanServer.invoke(on, "validateConditionalBreakpoint", new Object[] { "simple", "${body contains 'Camel'" },
                new String[] { "java.lang.String", "java.lang.String" });
        assertNotNull(out);
        assertTrue(out.toString().startsWith("Invalid syntax ${body contains 'Camel'"));

        // validate conditional breakpoint (is correct)
        out = mbeanServer.invoke(on, "validateConditionalBreakpoint", new Object[] { "simple", "${body} contains 'Camel'" },
                new String[] { "java.lang.String", "java.lang.String" });
        assertNull(out);

        // add breakpoint at bar
        mbeanServer.invoke(on, "addConditionalBreakpoint", new Object[] { "bar", "simple", "${body} contains 'Camel'" },
                new String[] { "java.lang.String", "java.lang.String", "java.lang.String" });

        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMessageCount(1);

        template.sendBody("seda:start", "Hello World");

        assertMockEndpointsSatisfied();

        // add not breakpoint at bar as condition did not match
        Set<String> nodes = (Set<String>) mbeanServer.invoke(on, "getSuspendedBreakpointNodeIds", null, null);
        assertNotNull(nodes);
        assertEquals(0, nodes.size());

        resetMocks();

        mock.expectedMessageCount(0);
        mock.setSleepForEmptyTest(100);

        template.sendBody("seda:start", "Hello Camel");

        assertMockEndpointsSatisfied();

        nodes = (Set<String>) mbeanServer.invoke(on, "getSuspendedBreakpointNodeIds", null, null);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        assertEquals("bar", nodes.iterator().next());

        // the message should be ours
        String xml = (String) mbeanServer.invoke(on, "dumpTracedMessagesAsXml", new Object[] { "bar" },
                new String[] { "java.lang.String" });
        assertNotNull(xml);
        log.info(xml);

        assertTrue(xml.contains("Hello Camel"), "Should contain our body");
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