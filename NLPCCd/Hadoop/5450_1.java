//,temp,sample_4510.java,2,8,temp,sample_8418.java,2,8
//,2
public class xxx {
public void testSerialSubmit() throws Exception {
policy = GridmixJobSubmissionPolicy.SERIAL;
doSubmission(JobCreator.SLEEPJOB.name(), false);


log.info("serial ended at");
}

};