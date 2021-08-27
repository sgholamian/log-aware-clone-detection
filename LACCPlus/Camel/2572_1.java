//,temp,SpringMicrometerMessageHistoryTest.java,44,83,temp,MicrometerMessageHistoryTest.java,53,91
//,3
public class xxx {
    @Test
    public void testMetricsHistory() throws Exception {
        int count = 10;

        getMockEndpoint("mock:foo").expectedMessageCount(count / 2);
        getMockEndpoint("mock:bar").expectedMessageCount(count / 2);
        getMockEndpoint("mock:baz").expectedMessageCount(count / 2);

        for (int i = 0; i < count; i++) {
            if (i % 2 == 0) {
                template.sendBody("direct:foo", "Hello " + i);
            } else {
                template.sendBody("direct:bar", "Hello " + i);
            }
        }

        assertMockEndpointsSatisfied();

        // there should be 3 names
        MeterRegistry registry = context.getRegistry().findByType(MeterRegistry.class).iterator().next();
        assertEquals(3, registry.getMeters().size());

        Timer fooTimer = registry.find(DEFAULT_CAMEL_MESSAGE_HISTORY_METER_NAME).tag(NODE_ID_TAG, "foo").timer();
        assertEquals(count / 2, fooTimer.count());
        Timer barTimer = registry.find(DEFAULT_CAMEL_MESSAGE_HISTORY_METER_NAME).tag(NODE_ID_TAG, "bar").timer();
        assertEquals(count / 2, barTimer.count());
        Timer bazTimer = registry.find(DEFAULT_CAMEL_MESSAGE_HISTORY_METER_NAME).tag(NODE_ID_TAG, "baz").timer();
        assertEquals(count / 2, bazTimer.count());

        // get the message history service
        MicrometerMessageHistoryService service = context.hasService(MicrometerMessageHistoryService.class);
        assertNotNull(service);
        String json = service.dumpStatisticsAsJson();
        assertNotNull(json);
        log.info(json);

        assertTrue(json.contains("\"nodeId\" : \"foo\""));
        assertTrue(json.contains("\"nodeId\" : \"bar\""));
        assertTrue(json.contains("\"nodeId\" : \"baz\""));
    }

};