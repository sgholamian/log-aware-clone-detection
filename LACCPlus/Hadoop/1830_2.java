//,temp,TestFileBasedCopyListing.java,323,343,temp,TestIntegration.java,325,342
//,3
public class xxx {
  @Test(timeout=100000)
  public void testUpdateMultiDirTargetMissing() {

    try {
      addEntries(listFile, "multifile", "singledir");
      createFiles("multifile/file3", "multifile/file4", "multifile/file5");
      mkdirs(root + "/singledir/dir1");

      runTest(listFile, target, false, true);

      checkResult(target, 4, "file3", "file4", "file5", "dir1");
    } catch (IOException e) {
      LOG.error("Exception encountered while testing distcp", e);
      Assert.fail("distcp failure");
    } finally {
      TestDistCpUtils.delete(fs, root);
    }
  }

};