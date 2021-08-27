//,temp,LevelDBAggregateSerializedHeadersTest.java,53,74,temp,JdbcAggregateLoadTest.java,30,48
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
            char id = 'A';
            LOG.debug("Sending {} with id {}", value, id);
            template.sendBodyAndHeader("seda:start?size=" + SIZE, value, "id", "" + id);
        }

        LOG.info("Sending all " + SIZE + " message done. Now waiting for aggregation to complete.");

        assertMockEndpointsSatisfied();
    }

};