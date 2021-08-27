//,temp,VMTransportThreadSafeTest.java,636,647,temp,VMTransportThreadSafeTest.java,623,634
//,3
public class xxx {
    @Test(timeout=120000)
    public void TestTwoWayMessageThroughPutSync() throws Exception {

        long totalTimes = 0;
        final long executions = 20;

        for (int i = 0; i < 20; ++i) {
            totalTimes += doTestTwoWayMessageThroughPut(false);
        }

        LOG.info("Total time of one way sync send throughput test: " + (totalTimes/executions) + "ms");
    }

};