//,temp,TestSleepJob.java,111,117,temp,TestSleepJob.java,94,101
//,2
public class xxx {
  @Test  (timeout=600000)
  public void testSerialSubmit() throws Exception {
    // set policy
    policy = GridmixJobSubmissionPolicy.SERIAL;
    LOG.info("Serial started at " + System.currentTimeMillis());
    doSubmission(JobCreator.SLEEPJOB.name(), false);
    LOG.info("Serial ended at " + System.currentTimeMillis());
  }

};