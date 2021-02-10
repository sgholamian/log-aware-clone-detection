//,temp,TestMapReduceJobControl.java,147,183,temp,TestMapReduceJobControl.java,124,145
//,3
public class xxx {
  public void testJobControlWithFailJob() throws Exception {
    LOG.info("Starting testJobControlWithFailJob");
    Configuration conf = createJobConf();

    cleanupData(conf);
    
    // create a Fail job
    Job job1 = MapReduceTestUtil.createFailJob(conf, outdir_1, indir);
    
    // create job dependencies
    JobControl theControl = createDependencies(conf, job1);
    
    // wait till all the jobs complete
    waitTillAllFinished(theControl);
    
    assertTrue(cjob1.getJobState() == ControlledJob.State.FAILED);
    assertTrue(cjob2.getJobState() == ControlledJob.State.SUCCESS);
    assertTrue(cjob3.getJobState() == ControlledJob.State.DEPENDENT_FAILED);
    assertTrue(cjob4.getJobState() == ControlledJob.State.DEPENDENT_FAILED);

    theControl.stop();
  }

};