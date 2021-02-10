//,temp,TestLoadJob.java,59,66,temp,TestSleepJob.java,94,101
//,2
public class xxx {
  @Test (timeout=500000)
  public void testSerialSubmit() throws Exception {
    policy = GridmixJobSubmissionPolicy.SERIAL;
    LOG.info("Serial started at " + System.currentTimeMillis());
    doSubmission(JobCreator.LOADJOB.name(), false);

    LOG.info("Serial ended at " + System.currentTimeMillis());
  }

};