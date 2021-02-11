//,temp,CommitProcessorTest.java,240,249,temp,CommitProcessorTest.java,214,224
//,3
public class xxx {
    @Test
    public void testNoCommitWorkersMixedWorkload() throws Exception {
        int numClients = 10;
        LOG.info("testNoCommitWorkersMixedWorkload 25w/75r workload test");
        setUp(0, numClients, 25);
        synchronized (this) {
            wait(TEST_RUN_TIME_IN_MS);
        }
        Assert.assertFalse(fail);
        checkProcessedRequest();
    }

};