//,temp,TransactedConsumeTest.java,47,60,temp,AMQ2439Test.java,45,55
//,3
public class xxx {
    @Test
    public void testConsume() throws Exception {

        LOG.info("Wait for dequeue message...");

        assertTrue(Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return broker.getAdminView().getTotalDequeueCount() >= messageCount;
            }
        }, 20 * 60 * 1000));
        long duration = System.currentTimeMillis() - firstConsumed.get();
        LOG.info("Done message consumption in " + duration + "millis");
    }

};