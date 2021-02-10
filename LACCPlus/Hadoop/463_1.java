//,temp,TestMapReduceJobControl.java,147,183,temp,TestMapReduceJobControl.java,124,145
//,3
public class xxx {
  public void testJobControlWithKillJob() throws Exception {
    LOG.info("Starting testJobControlWithKillJob");

    Configuration conf = createJobConf();
    cleanupData(conf);
    Job job1 = MapReduceTestUtil.createKillJob(conf, outdir_1, indir);
    JobControl theControl = createDependencies(conf, job1);

    while (cjob1.getJobState() != ControlledJob.State.RUNNING) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        break;
      }
    }
    // verify adding dependingJo to RUNNING job fails.
    assertFalse(cjob1.addDependingJob(cjob2));

    // suspend jobcontrol and resume it again
    theControl.suspend();
    assertTrue(
      theControl.getThreadState() == JobControl.ThreadState.SUSPENDED);
    theControl.resume();
    
    // kill the first job.
    cjob1.killJob();

    // wait till all the jobs complete
    waitTillAllFinished(theControl);
    
    assertTrue(cjob1.getJobState() == ControlledJob.State.FAILED);
    assertTrue(cjob2.getJobState() == ControlledJob.State.SUCCESS);
    assertTrue(cjob3.getJobState() == ControlledJob.State.DEPENDENT_FAILED);
    assertTrue(cjob4.getJobState() == ControlledJob.State.DEPENDENT_FAILED);

    theControl.stop();
  }

};