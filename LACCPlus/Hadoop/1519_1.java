//,temp,TestDistCpViewFs.java,376,398,temp,TestDistCpViewFs.java,128,144
//,3
public class xxx {
  @Test
  public void testGlobTargetMissingMultiLevel() {

    try {
      Path listFile = new Path("target/tmp1/listing").makeQualified(fs.getUri(),
              fs.getWorkingDirectory());
      addEntries(listFile, "*/*");
      createFiles("multifile/file3", "multifile/file4", "multifile/file5");
      createFiles("singledir1/dir3/file7", "singledir1/dir3/file8",
          "singledir1/dir3/file9");

      runTest(listFile, target, false, false);

      checkResult(target, 4, "file3", "file4", "file5",
          "dir3/file7", "dir3/file8", "dir3/file9");
    } catch (IOException e) {
      LOG.error("Exception encountered while running distcp", e);
      Assert.fail("distcp failure");
    } finally {
      TestDistCpUtils.delete(fs, root);
      TestDistCpUtils.delete(fs, "target/tmp1");
    }
  }

};