//,temp,TestDistCpViewFs.java,331,352,temp,TestDistCpViewFs.java,292,310
//,3
public class xxx {
  @Test
  public void testGlobTargetMissingSingleLevel() {

    try {
      Path listFile = new Path("target/tmp1/listing").makeQualified(fs.getUri(),
                                fs.getWorkingDirectory());
      addEntries(listFile, "*");
      createFiles("multifile/file3", "multifile/file4", "multifile/file5");
      createFiles("singledir/dir2/file6");

      runTest(listFile, target, false, false);

      checkResult(target, 2, "multifile/file3", "multifile/file4", "multifile/file5",
          "singledir/dir2/file6");
    } catch (IOException e) {
      LOG.error("Exception encountered while testing distcp", e);
      Assert.fail("distcp failure");
    } finally {
      TestDistCpUtils.delete(fs, root);
      TestDistCpUtils.delete(fs, "target/tmp1");
    }
  }

};