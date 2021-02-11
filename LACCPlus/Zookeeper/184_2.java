//,temp,CommitProcessorTest.java,266,275,temp,CommitProcessorTest.java,240,249
//,3
public class xxx {
    @Test
    public void testOneCommitWorkerMixedWorkload() throws Exception {
        setUp(1, 10, 25);
        LOG.info("testOneCommitWorkerMixedWorkload 25w/75r workload test");
        synchronized(this) {
            wait(TEST_RUN_TIME_IN_MS);
        }
        Assert.assertFalse(fail);
        checkProcessedRequest();
    }

};