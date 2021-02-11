//,temp,CommitProcessorTest.java,266,275,temp,CommitProcessorTest.java,214,224
//,3
public class xxx {
    @Test
    public void testManyCommitWorkersMixedWorkload() throws Exception {
        setUp(16, 8 , 8, 25);
        LOG.info("testManyCommitWorkersMixedWorkload 8X0w/100r + 8X25w/75r workload test");
        synchronized(this) {
            wait(TEST_RUN_TIME_IN_MS);
        }
        Assert.assertFalse(fail);
        checkProcessedRequest();
    }

};