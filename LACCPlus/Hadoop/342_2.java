//,temp,TestDistCpViewFs.java,292,310,temp,TestDistCpViewFs.java,273,290
//,3
public class xxx {
  @Test
  public void testUpdateMultiDirTargetPresent() {

    try {
      addEntries(listFile, "Umultifile", "Usingledir");
      createFiles("Umultifile/Ufile3", "Umultifile/Ufile4", "Umultifile/Ufile5");
      mkdirs(target.toString(), root + "/Usingledir/Udir1");

      runTest(listFile, target, true, true);

      checkResult(target, 4, "Ufile3", "Ufile4", "Ufile5", "Udir1");
    } catch (IOException e) {
      LOG.error("Exception encountered while testing distcp", e);
      Assert.fail("distcp failure");
    } finally {
      TestDistCpUtils.delete(fs, root);
    }
  }

};