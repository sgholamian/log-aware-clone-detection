//,temp,PerformanceRoutePojoTest.java,39,65,temp,PerformanceRouteTest.java,39,69
//,3
public class xxx {
    @Test
    public void testPojoPerformance() throws Exception {
        long start = System.currentTimeMillis();

        getMockEndpoint("mock:audit").expectedMessageCount(size);
        getMockEndpoint("mock:audit").expectsNoDuplicates().body();

        getMockEndpoint("mock:gold").expectedMessageCount((size / 2) - (size / 10));
        getMockEndpoint("mock:silver").expectedMessageCount(size / 10);

        for (int i = 0; i < size; i++) {
            String type;
            if (i % 10 == 0) {
                type = "silver";
            } else if (i % 2 == 0) {
                type = "gold";
            } else {
                type = "bronze";
            }
            template.sendBodyAndHeader("activemq:queue:inbox", "Message " + i, "type", type);
        }

        assertMockEndpointsSatisfied();

        long delta = System.currentTimeMillis() - start;
        LOG.info("RoutePerformancePojoTest: Sent: " + size + " Took: " + delta + " ms");
    }

};