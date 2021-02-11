//,temp,TestJobHistoryParsing.java,815,865,temp,TestJobHistoryParsing.java,658,702
//,3
public class xxx {
  @Test(timeout = 50000)
  public void testScanningOldDirs() throws Exception {
    LOG.info("STARTING testScanningOldDirs");
    try {
      Configuration conf = new Configuration();
      conf.setClass(
          NET_TOPOLOGY_NODE_SWITCH_MAPPING_IMPL_KEY,
          MyResolver.class, DNSToSwitchMapping.class);
      RackResolver.init(conf);
      MRApp app = new MRAppWithHistory(1, 1, true, this.getClass().getName(),
          true);
      app.submit(conf);
      Job job = app.getContext().getAllJobs().values().iterator().next();
      JobId jobId = job.getID();
      LOG.info("JOBID is " + TypeConverter.fromYarn(jobId).toString());
      app.waitForState(job, JobState.SUCCEEDED);

      // make sure all events are flushed
      app.waitForState(Service.STATE.STOPPED);

      HistoryFileManagerForTest hfm = new HistoryFileManagerForTest();
      hfm.init(conf);
      HistoryFileInfo fileInfo = hfm.getFileInfo(jobId);
      Assert.assertNotNull("Unable to locate job history", fileInfo);

      // force the manager to "forget" the job
      hfm.deleteJobFromJobListCache(fileInfo);
      final int msecPerSleep = 10;
      int msecToSleep = 10 * 1000;
      while (fileInfo.isMovePending() && msecToSleep > 0) {
        Assert.assertTrue(!fileInfo.didMoveFail());
        msecToSleep -= msecPerSleep;
        Thread.sleep(msecPerSleep);
      }
      Assert.assertTrue("Timeout waiting for history move", msecToSleep > 0);

      fileInfo = hfm.getFileInfo(jobId);
      hfm.stop();
      Assert.assertNotNull("Unable to locate old job history", fileInfo);
      Assert.assertTrue("HistoryFileManager not shutdown properly",
          hfm.moveToDoneExecutor.isTerminated());
    } finally {
      LOG.info("FINISHED testScanningOldDirs");
    }
  }

};