//,temp,TestMRAppMaster.java,260,296,temp,TestMRAppMaster.java,146,181
//,3
public class xxx {
  @Test
  public void testMRAppMasterFailLock() throws IOException,
      InterruptedException {
    String applicationAttemptIdStr = "appattempt_1317529182569_0004_000002";
    String containerIdStr = "container_1317529182569_0004_000002_1";
    String userName = "TestAppMasterUser";
    JobConf conf = new JobConf();
    conf.set(MRJobConfig.MR_AM_STAGING_DIR, stagingDir);
    ApplicationAttemptId applicationAttemptId = ConverterUtils
        .toApplicationAttemptId(applicationAttemptIdStr);
    JobId jobId =  TypeConverter.toYarn(
        TypeConverter.fromYarn(applicationAttemptId.getApplicationId()));
    Path start = MRApps.getStartJobCommitFile(conf, userName, jobId);
    Path end = MRApps.getEndJobCommitFailureFile(conf, userName, jobId);
    FileSystem fs = FileSystem.get(conf);
    fs.create(start).close();
    fs.create(end).close();
    ContainerId containerId = ConverterUtils.toContainerId(containerIdStr);
    MRAppMaster appMaster =
        new MRAppMasterTest(applicationAttemptId, containerId, "host", -1, -1,
            System.currentTimeMillis(), false, false);
    boolean caught = false;
    try {
      MRAppMaster.initAndStartAppMaster(appMaster, conf, userName);
    } catch (IOException e) {
      //The IO Exception is expected
      LOG.info("Caught expected Exception", e);
      caught = true;
    }
    assertTrue(caught);
    assertTrue(appMaster.errorHappenedShutDown);
    assertEquals(JobStateInternal.FAILED, appMaster.forcedState);
    appMaster.stop();

    // verify the final status is FAILED
    verifyFailedStatus((MRAppMasterTest)appMaster, "FAILED");
  }

};