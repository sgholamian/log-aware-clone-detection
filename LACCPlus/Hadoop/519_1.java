//,temp,TestSleepJob.java,111,117,temp,TestSleepJob.java,94,101
//,2
public class xxx {
  @Test   (timeout=600000)
  public void testStressSubmit() throws Exception {
    policy = GridmixJobSubmissionPolicy.STRESS;
    LOG.info(" Replay started at " + System.currentTimeMillis());
    doSubmission(JobCreator.SLEEPJOB.name(), false);
    LOG.info(" Replay ended at " + System.currentTimeMillis());
  }

};