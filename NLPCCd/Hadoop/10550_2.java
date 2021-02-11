//,temp,sample_3359.java,2,17,temp,sample_3357.java,2,17
//,2
public class xxx {
public void dummy_method(){
dependingJobs.add(job_3);
Job job_4 = new Job(jobConf_4, dependingJobs);
JobControl theControl = new JobControl("Test");
theControl.addJob(job_1);
theControl.addJob(job_2);
theControl.addJob(job_3);
theControl.addJob(job_4);
Thread theController = new Thread(theControl);
theController.start();
while (!theControl.allFinished()) {


log.info("jobs in running state");
}
}

};