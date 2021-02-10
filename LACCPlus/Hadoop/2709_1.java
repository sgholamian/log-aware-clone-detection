//,temp,TestLoadJob.java,60,67,temp,TestGridmixSubmission.java,156,163
//,3
public class xxx {
  @Test (timeout=500000)
  public void testSerialSubmit() throws Exception {
    policy = GridmixJobSubmissionPolicy.SERIAL;
    LOG.info("Serial started at " + System.currentTimeMillis());
    doSubmission(JobCreator.LOADJOB.name(), false);

    LOG.info("Serial ended at " + System.currentTimeMillis());
  }

};