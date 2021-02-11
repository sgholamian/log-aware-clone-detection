//,temp,TestDistCpViewFs.java,312,329,temp,TestIntegration.java,165,180
//,3
public class xxx {
  @Test
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