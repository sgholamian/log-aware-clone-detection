//,temp,TestIntegration.java,325,342,temp,TestIntegration.java,305,323
//,3
public class xxx {
  @Test(timeout=100000)
  public void testMultiDirTargetMissing() {

    try {
      addEntries(listFile, "multifile", "singledir");
      createFiles("multifile/file3", "multifile/file4", "multifile/file5");
      mkdirs(root + "/singledir/dir1");

      runTest(listFile, target, false, false);

      checkResult(target, 2, "multifile/file3", "multifile/file4",
          "multifile/file5", "singledir/dir1");
    } catch (IOException e) {
      LOG.error("Exception encountered while testing distcp", e);
      Assert.fail("distcp failure");
    } finally {
      TestDistCpUtils.delete(fs, root);
    }
  }

};