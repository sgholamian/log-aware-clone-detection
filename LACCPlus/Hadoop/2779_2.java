//,temp,TestIntegration.java,266,283,temp,TestIntegration.java,200,217
//,3
public class xxx {
  @Test(timeout=100000)
  public void testUpdateSingleDirTargetPresent() {

    try {
      addEntries(listFile, "Usingledir");
      mkdirs(root + "/Usingledir/Udir1");
      mkdirs(target.toString());

      runTest(listFile, target, true, true);

      checkResult(target, 1, "Udir1");
    } catch (IOException e) {
      LOG.error("Exception encountered while testing distcp", e);
      Assert.fail("distcp failure");
    } finally {
      TestDistCpUtils.delete(fs, root);
    }
  }

};