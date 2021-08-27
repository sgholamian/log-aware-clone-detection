//,temp,TestSessionState.java,275,316,temp,TestSessionState.java,205,231
//,3
public class xxx {
  @Test
  public void testReloadExistingAuxJars2() {
    HiveConf conf = new HiveConf();
    HiveConf.setVar(conf, ConfVars.HIVERELOADABLEJARS, hiveReloadPath);

    SessionState ss = new SessionState(conf);
    SessionState.start(ss);
    File dist = null;

    try {
      ss = SessionState.get();

      LOG.info("copy jar file 1");
      dist = new File(reloadFolder.getAbsolutePath() + File.separator + reloadClazzFileName);

      Files.copy(new File(HiveTestUtils.getFileFromClasspath(clazzDistFileName)), dist);
      ss.loadReloadableAuxJars();

      Assert.assertEquals("version1", getReloadedClazzVersion(ss.getConf().getClassLoader()));

      LOG.info("copy jar file 2");
      FileUtils.deleteQuietly(dist);
      Files.copy(new File(HiveTestUtils.getFileFromClasspath(clazzV2FileName)), dist);

      ss.loadReloadableAuxJars();
      Assert.assertEquals("version2", getReloadedClazzVersion(ss.getConf().getClassLoader()));

      FileUtils.deleteQuietly(dist);
      ss.loadReloadableAuxJars();
    } catch (Exception e) {
      LOG.error("refresh existing jar file case failed with message: ", e);
      Assert.fail(e.getMessage());
    } finally {
      FileUtils.deleteQuietly(dist);
      try {
        ss.close();
      } catch (IOException ioException) {
        Assert.fail(ioException.getMessage());
        LOG.error("Fail to close the created session: ", ioException);
      }
    }
  }

};