//,temp,LevelDBAggregateLoadTest.java,48,66,temp,JdbcAggregateSerializedHeadersTest.java,31,49
//,3
public class xxx {
    @Test
    public void testLoadTestJdbcAggregate() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(1);
        mock.setResultWaitTime(50 * 1000);

        LOG.info("Staring to send " + SIZE + " messages.");

        for (int i = 0; i < SIZE; i++) {
            final int value = 1;
            HeaderDto headerDto = new HeaderDto("org", "company", 1);
            LOG.debug("Sending {} with id {}", value, headerDto);
            template.sendBodyAndHeader("seda:start?size=" + SIZE, value, "id", headerDto);
        }

        LOG.info("Sending all " + SIZE + " message done. Now waiting for aggregation to complete.");

        assertMockEndpointsSatisfied();
    }

};