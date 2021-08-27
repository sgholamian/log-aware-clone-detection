//,temp,VMTransportThreadSafeTest.java,746,757,temp,VMTransportThreadSafeTest.java,733,744
//,3
public class xxx {
    @Test(timeout=120000)
    public void TestOneWayMessageThroughPutAsnyc() throws Exception {

        long totalTimes = 0;
        final long executions = 20;

        for (int i = 0; i < 20; ++i) {
            totalTimes += doTestOneWayMessageThroughPut(true);
        }

        LOG.info("Total time of one way async send throughput test: " + (totalTimes/executions) + "ms");
    }

};