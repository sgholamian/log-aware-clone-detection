//,temp,TestMRJobs.java,431,479,temp,TestMRJobs.java,199,243
//,3
public class xxx {
  private void testSleepJobInternal(boolean useRemoteJar) throws Exception {
    LOG.info("\n\n\nStarting testSleepJob: useRemoteJar=" + useRemoteJar);

    if (!(new File(MiniMRYarnCluster.APPJAR)).exists()) {
      LOG.info("MRAppJar " + MiniMRYarnCluster.APPJAR
               + " not found. Not running test.");
      return;
    }

    Configuration sleepConf = new Configuration(mrCluster.getConfig());
    // set master address to local to test that local mode applied iff framework == local
    sleepConf.set(MRConfig.MASTER_ADDRESS, "local");	
    
    SleepJob sleepJob = new SleepJob();
    sleepJob.setConf(sleepConf);
   
    // job with 3 maps (10s) and numReduces reduces (5s), 1 "record" each:
    Job job = sleepJob.createJob(3, numSleepReducers, 10000, 1, 5000, 1);

    job.addFileToClassPath(APP_JAR); // The AppMaster jar itself.
    if (useRemoteJar) {
      final Path localJar = new Path(
          ClassUtil.findContainingJar(SleepJob.class));
      ConfigUtil.addLink(job.getConfiguration(), "/jobjars",
          localFs.makeQualified(localJar.getParent()).toUri());
      job.setJar("viewfs:///jobjars/" + localJar.getName());
    } else {
      job.setJarByClass(SleepJob.class);
    }
    job.setMaxMapAttempts(1); // speed up failures
    job.submit();
    String trackingUrl = job.getTrackingURL();
    String jobId = job.getJobID().toString();
    boolean succeeded = job.waitForCompletion(true);
    Assert.assertTrue(succeeded);
    Assert.assertEquals(JobStatus.State.SUCCEEDED, job.getJobState());
    Assert.assertTrue("Tracking URL was " + trackingUrl +
                      " but didn't Match Job ID " + jobId ,
          trackingUrl.endsWith(jobId.substring(jobId.lastIndexOf("_")) + "/"));
    verifySleepJobCounters(job);
    verifyTaskProgress(job);
    
    // TODO later:  add explicit "isUber()" checks of some sort (extend
    // JobStatus?)--compare against MRJobConfig.JOB_UBERTASK_ENABLE value
  }

};