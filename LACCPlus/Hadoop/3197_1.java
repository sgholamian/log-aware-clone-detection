//,temp,TestJobHistoryParsing.java,815,865,temp,TestJobHistoryParsing.java,658,702
//,3
public class xxx {
  @Test(timeout = 15000)
  public void testDeleteFileInfo() throws Exception {
    LOG.info("STARTING testDeleteFileInfo");
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

      app.waitForState(job, JobState.SUCCEEDED);

      // make sure all events are flushed
      app.waitForState(Service.STATE.STOPPED);

      HistoryFileManager hfm = new HistoryFileManager();
      hfm.init(conf);
      HistoryFileInfo fileInfo = hfm.getFileInfo(jobId);
      hfm.initExisting();
      // wait for move files form the done_intermediate directory to the gone
      // directory
      while (fileInfo.isMovePending()) {
        Thread.sleep(300);
      }

      Assert.assertNotNull(hfm.jobListCache.values());

      // try to remove fileInfo
      hfm.clean();
      // check that fileInfo does not deleted
      Assert.assertFalse(fileInfo.isDeleted());
      // correct live time
      hfm.setMaxHistoryAge(-1);
      hfm.clean();
      hfm.stop();
      Assert.assertTrue("Thread pool shutdown",
          hfm.moveToDoneExecutor.isTerminated());
      // should be deleted !
      Assert.assertTrue("file should be deleted ", fileInfo.isDeleted());

    } finally {
      LOG.info("FINISHED testDeleteFileInfo");
    }
  }

};