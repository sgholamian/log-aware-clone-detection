//,temp,CommitProcessorTest.java,252,264,temp,CommitProcessorTest.java,200,212
//,2
public class xxx {
    @Test
    public void testNoCommitWorkersReadOnlyWorkload() throws Exception {
        int numClients = 10;
        LOG.info("testNoCommitWorkersReadOnlyWorkload");
        setUp(0, numClients, 0);
        synchronized (this) {
            wait(TEST_RUN_TIME_IN_MS);
        }
        Assert.assertFalse(fail);
        Assert.assertTrue("No read requests processed", processedReadRequests.get() > 0);
        // processedWriteRequests.get() == numClients since each client performs one write at the beginning (creates a znode)
        Assert.assertTrue("Write requests processed", processedWriteRequests.get() == numClients);
    }

};