//,temp,TestLoadJob.java,72,79,temp,TestSleepJob.java,94,101
//,3
public class xxx {
  @Test  (timeout=500000)
  public void testReplaySubmit() throws Exception {
    policy = GridmixJobSubmissionPolicy.REPLAY;
    LOG.info(" Replay started at " + System.currentTimeMillis());
    doSubmission(JobCreator.LOADJOB.name(), false);

    LOG.info(" Replay ended at " + System.currentTimeMillis());
  }

};