//,temp,sample_4514.java,2,8,temp,sample_8416.java,2,8
//,2
public class xxx {
public void testSerialSubmit() throws Exception {
policy = GridmixJobSubmissionPolicy.SERIAL;
doSubmission(JobCreator.LOADJOB.name(), false);


log.info("serial ended at");
}

};