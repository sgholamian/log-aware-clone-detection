//,temp,TestLoadJob.java,72,79,temp,TestSleepJob.java,94,101
//,3
public class xxx {
  @Test
  public void testSerialSubmit() throws Exception {
    // set policy
    policy = GridmixJobSubmissionPolicy.SERIAL;
    LOG.info("Serial started at " + System.currentTimeMillis());
    doSubmission(JobCreator.SLEEPJOB.name(), false);
    LOG.info("Serial ended at " + System.currentTimeMillis());
  }

};