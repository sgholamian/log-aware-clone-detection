//,temp,TestBlockRecovery.java,187,201,temp,TestDatanodeProtocolRetryPolicy.java,115,130
//,3
public class xxx {
  @After
  public void tearDown() throws IOException {
    if (!tearDownDone && dn != null) {
      try {
        dn.shutdown();
      } catch(Exception e) {
        LOG.error("Cannot close: ", e);
      } finally {
        File dir = new File(DATA_DIR);
        if (dir.exists())
          Assert.assertTrue(
              "Cannot delete data-node dirs", FileUtil.fullyDelete(dir));
      }
      tearDownDone = true;
    }
  }

};