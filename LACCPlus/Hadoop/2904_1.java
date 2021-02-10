//,temp,TestFileBasedCopyListing.java,351,370,temp,TestIntegration.java,225,241
//,3
public class xxx {
  private void caseMultiDirTargetMissing(boolean sync) {

    try {
      Path listFile = new Path("/tmp/listing");
      Path target = new Path("/tmp/target");

      addEntries(listFile, "/tmp/multifile", "/tmp/singledir");
      createFiles("/tmp/multifile/file3", "/tmp/multifile/file4", "/tmp/multifile/file5");
      mkdirs("/tmp/singledir/dir1");

      runTest(listFile, target, sync);

      checkResult(listFile, 4);
    } catch (IOException e) {
      LOG.error("Exception encountered while testing build listing", e);
      Assert.fail("build listing failure");
    } finally {
      TestDistCpUtils.delete(fs, "/tmp");
    }
  }

};