//,temp,ExceptionRouteMetricsMessageHistoryTest.java,51,81,temp,MetricsMessageHistoryTest.java,49,78
//,3
public class xxx {
    @Test
    public void testMetricsHistory() throws Exception {
        getMockEndpoint("mock:foo").expectedMessageCount(5);
        getMockEndpoint("mock:bar").expectedMessageCount(5);
        getMockEndpoint("mock:baz").expectedMessageCount(5);

        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                template.sendBody("seda:foo", "Hello " + i);
            } else {
                template.sendBody("seda:bar", "Hello " + i);
            }
        }

        assertMockEndpointsSatisfied();

        // there should be 3 names
        assertEquals(3, registry.getNames().size());

        // get the message history service
        MetricsMessageHistoryService service = context.hasService(MetricsMessageHistoryService.class);
        assertNotNull(service);
        String json = service.dumpStatisticsAsJson();
        assertNotNull(json);
        log.info(json);

        assertTrue(json.contains("foo.history"));
        assertTrue(json.contains("bar.history"));
        assertTrue(json.contains("baz.history"));
    }

};