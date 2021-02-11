//,temp,TestLoadJob.java,71,78,temp,TestSleepJob.java,103,109
//,2
public class xxx {
  @Test  (timeout=500000)
  public void testReplaySubmit() throws Exception {
    policy = GridmixJobSubmissionPolicy.REPLAY;
    LOG.info(" Replay started at " + System.currentTimeMillis());
    doSubmission(JobCreator.LOADJOB.name(), false);

    LOG.info(" Replay ended at " + System.currentTimeMillis());
  }

};