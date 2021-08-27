//,temp,TestSessionState.java,275,316,temp,TestSessionState.java,205,231
//,3
public class xxx {
  @Test
  public void testReloadAuxJars2() {
    HiveConf conf = new HiveConf();
    HiveConf.setVar(conf, ConfVars.HIVERELOADABLEJARS, hiveReloadPath);
    SessionState ss = new SessionState(conf);
    SessionState.start(ss);

    ss = SessionState.get();
    File dist = null;
    try {
      dist = new File(reloadFolder.getAbsolutePath() + File.separator + reloadClazzFileName);
      Files.copy(new File(HiveTestUtils.getFileFromClasspath(clazzDistFileName)), dist);
      ss.loadReloadableAuxJars();
      Assert.assertEquals("version1", getReloadedClazzVersion(ss.getConf().getClassLoader()));
    } catch (Exception e) {
      LOG.error("Reload auxiliary jar test fail with message: ", e);
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