//,temp,JdbcAggregateLoadConcurrentTest.java,35,64,temp,LevelDBAggregateLoadConcurrentTest.java,51,80
//,2
public class xxx {
    @Test
    public void testLoadTestJdbcAggregate() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMessageCount(10);
        mock.setResultWaitTime(50 * 1000);

        ExecutorService executor = Executors.newFixedThreadPool(10);

        LOG.info("Staring to send " + SIZE + " messages.");

        for (int i = 0; i < SIZE; i++) {
            final int value = 1;
            final int key = i % 10;
            executor.submit(new Callable<Object>() {
                public Object call() throws Exception {
                    char id = KEYS[key];
                    LOG.debug("Sending {} with id {}", value, id);
                    template.sendBodyAndHeader("direct:start", value, "id", "" + id);
                    // simulate a little delay
                    Thread.sleep(3);
                    return null;
                }
            });
        }

        LOG.info("Sending all " + SIZE + " message done. Now waiting for aggregation to complete.");

        assertMockEndpointsSatisfied();
        executor.shutdownNow();
    }

};