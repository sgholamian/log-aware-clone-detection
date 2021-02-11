//,temp,TestFileBasedCopyListing.java,254,273,temp,TestIntegration.java,419,439
//,3
public class xxx {
  @Test(timeout=100000)
  public void testUpdateGlobTargetMissingSingleLevel() {

    try {
      Path listFile = new Path("target/tmp1/listing").makeQualified(fs.getUri(),
                                  fs.getWorkingDirectory());
      addEntries(listFile, "*");
      createFiles("multifile/file3", "multifile/file4", "multifile/file5");
      createFiles("singledir/dir2/file6");

      runTest(listFile, target, false, true);

      checkResult(target, 4, "file3", "file4", "file5", "dir2/file6");
    } catch (IOException e) {
      LOG.error("Exception encountered while running distcp", e);
      Assert.fail("distcp failure");
    } finally {
      TestDistCpUtils.delete(fs, root);
      TestDistCpUtils.delete(fs, "target/tmp1");
    }
  }

};