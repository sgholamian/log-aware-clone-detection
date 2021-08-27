//,temp,VMTransportThreadSafeTest.java,746,757,temp,VMTransportThreadSafeTest.java,733,744
//,3
public class xxx {
    @Test(timeout=120000)
    public void TestOneWayMessageThroughPutSync() throws Exception {

        long totalTimes = 0;
        final long executions = 30;

        for (int i = 0; i < executions; ++i) {
            totalTimes += doTestOneWayMessageThroughPut(false);
        }

        LOG.info("Total time of one way sync send throughput test: " + (totalTimes/executions) + "ms");
    }

};