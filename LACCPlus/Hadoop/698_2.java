//,temp,TestIntegration.java,305,323,temp,TestIntegration.java,165,180
//,3
public class xxx {
  private void caseSingleDirTargetMissing(boolean sync) {

    try {
      addEntries(listFile, "singledir");
      mkdirs(root + "/singledir/dir1");

      runTest(listFile, target, false, sync);

      checkResult(target, 1, "dir1");
    } catch (IOException e) {
      LOG.error("Exception encountered while testing distcp", e);
      Assert.fail("distcp failure");
    } finally {
      TestDistCpUtils.delete(fs, root);
    }
  }

};