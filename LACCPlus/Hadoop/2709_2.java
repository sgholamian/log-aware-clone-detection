//,temp,TestLoadJob.java,60,67,temp,TestGridmixSubmission.java,156,163
//,3
public class xxx {
  @Test (timeout=500000)
  public void testReplaySubmit() throws Exception {
    policy = GridmixJobSubmissionPolicy.REPLAY;
    LOG.info(" Replay started at " + System.currentTimeMillis());
    doSubmission(null, false);
    LOG.info(" Replay ended at " + System.currentTimeMillis());

  }

};