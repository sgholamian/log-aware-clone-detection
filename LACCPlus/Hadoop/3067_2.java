//,temp,TestMapReduceJobControl.java,210,231,temp,TestMapReduceJobControl.java,190,208
//,3
public class xxx {
  @Test
  public void testJobControl() throws Exception {
    LOG.info("Starting testJobControl");

    Configuration conf = createJobConf();

    cleanupData(conf);
    
    Job job1 = MapReduceTestUtil.createCopyJob(conf, outdir_1, indir);
    
    JobControl theControl = createDependencies(conf, job1);
    
    // wait till all the jobs complete
    waitTillAllFinished(theControl);
    
    assertEquals("Some jobs failed", 0, theControl.getFailedJobList().size());
    
    theControl.stop();
  }

};