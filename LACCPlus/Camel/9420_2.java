//,temp,JdbcAggregateLoadAndRecoverTest.java,39,71,temp,LevelDBAggregateLoadAndRecoverTest.java,54,91
//,3
public class xxx {
    @Test
    public void testLoadAndRecoverLevelDBAggregate() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMessageCount(SIZE / 10);
        mock.setResultWaitTime(50 * 1000);

        LOG.info("Staring to send " + SIZE + " messages.");

        for (int i = 0; i < SIZE; i++) {
            final int value = 1;
            char id = 'A';
            Map<String, Object> headers = new HashMap<>();
            headers.put("id", id);
            headers.put("seq", i);
            LOG.debug("Sending {} with id {}", value, id);
            template.sendBodyAndHeaders("seda:start", value, headers);
            // simulate a little delay
            Thread.sleep(5);
        }

        LOG.info("Sending all " + SIZE + " message done. Now waiting for aggregation to complete.");

        assertMockEndpointsSatisfied();

        int recovered = 0;
        for (Exchange exchange : mock.getReceivedExchanges()) {
            if (exchange.getIn().getHeader(Exchange.REDELIVERED) != null) {
                recovered++;
            }
        }
        int expected = SIZE / 10 / 10;
        int delta = Math.abs(expected - recovered);
        if (delta == 0) {
            assertEquals(expected, recovered, "There should be " + expected + " recovered");
        } else {
            assertTrue(delta < 3, "We expected " + expected + " recovered but the delta is within accepted range " + delta);
        }
    }

};