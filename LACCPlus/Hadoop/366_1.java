//,temp,TestDistCpViewFs.java,400,422,temp,TestIntegration.java,396,417
//,3
public class xxx {
  @Test
  public void testUpdateGlobTargetMissingMultiLevel() {

    try {
      Path listFile = new Path("target/tmp1/listing").makeQualified(fs.getUri(),
              fs.getWorkingDirectory());
      addEntries(listFile, "*/*");
      createFiles("multifile/file3", "multifile/file4", "multifile/file5");
      createFiles("singledir1/dir3/file7", "singledir1/dir3/file8",
          "singledir1/dir3/file9");

      runTest(listFile, target, false, true);

      checkResult(target, 6, "file3", "file4", "file5",
          "file7", "file8", "file9");
    } catch (IOException e) {
      LOG.error("Exception encountered while running distcp", e);
      Assert.fail("distcp failure");
    } finally {
      TestDistCpUtils.delete(fs, root);
      TestDistCpUtils.delete(fs, "target/tmp1");
    }
  }

};