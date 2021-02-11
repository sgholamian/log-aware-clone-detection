//,temp,TestDistCpViewFs.java,152,167,temp,TestFileBasedCopyListing.java,184,202
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