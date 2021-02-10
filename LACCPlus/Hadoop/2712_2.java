//,temp,TestLoadJob.java,72,79,temp,TestSleepJob.java,111,117
//,3
public class xxx {
  @Test
  public void testStressSubmit() throws Exception {
    policy = GridmixJobSubmissionPolicy.STRESS;
    LOG.info(" Replay started at " + System.currentTimeMillis());
    doSubmission(JobCreator.SLEEPJOB.name(), false);
    LOG.info(" Replay ended at " + System.currentTimeMillis());
  }

};