//,temp,VMTransportThreadSafeTest.java,636,647,temp,VMTransportThreadSafeTest.java,623,634
//,3
public class xxx {
    @Test(timeout=120000)
    public void TestTwoWayMessageThroughPutAsnyc() throws Exception {

        long totalTimes = 0;
        final long executions = 50;

        for (int i = 0; i < executions; ++i) {
            totalTimes += doTestTwoWayMessageThroughPut(false);
        }

        LOG.info("Total time of one way async send throughput test: " + (totalTimes/executions) + "ms");
    }

};