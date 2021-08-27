//,temp,LevelDBAggregateLoadTest.java,48,66,temp,JdbcAggregateSerializedHeadersTest.java,31,49
//,3
public class xxx {
    @Test
    public void testLoadTestLevelDBAggregate() throws Exception {
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