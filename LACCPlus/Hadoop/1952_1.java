//,temp,TestDistCpViewFs.java,254,271,temp,TestDistCpViewFs.java,82,97
//,3
public class xxx {
  @Test
  public void testMultiDirTargetPresent() {

    try {
      addEntries(listFile, "multifile", "singledir");
      createFiles("multifile/file3", "multifile/file4", "multifile/file5");
      mkdirs(target.toString(), root + "/singledir/dir1");

      runTest(listFile, target, true, false);

      checkResult(target, 2, "multifile/file3", "multifile/file4", "multifile/file5", "singledir/dir1");
    } catch (IOException e) {
      LOG.error("Exception encountered while testing distcp", e);
      Assert.fail("distcp failure");
    } finally {
      TestDistCpUtils.delete(fs, root);
    }
  }

};