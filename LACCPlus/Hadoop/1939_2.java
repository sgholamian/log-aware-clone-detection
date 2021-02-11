//,temp,TestDistCpViewFs.java,188,205,temp,TestDistCpViewFs.java,169,186
//,3
public class xxx {
  @Test
  public void testSingleDirTargetPresent() {

    try {
      addEntries(listFile, "singledir");
      mkdirs(root + "/singledir/dir1");
      mkdirs(target.toString());

      runTest(listFile, target, true, false);

      checkResult(target, 1, "singledir/dir1");
    } catch (IOException e) {
      LOG.error("Exception encountered while testing distcp", e);
      Assert.fail("distcp failure");
    } finally {
      TestDistCpUtils.delete(fs, root);
    }
  }

};