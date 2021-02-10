//,temp,TestDistCpViewFs.java,128,144,temp,TestFileBasedCopyListing.java,105,123
//,3
public class xxx {
  private void caseSingleFileMissingTarget(boolean sync) {

    try {
      Path listFile = new Path("/tmp/listing");
      Path target = new Path("/tmp/target");

      addEntries(listFile, "/tmp/singlefile1/file1");
      createFiles("/tmp/singlefile1/file1");

      runTest(listFile, target, false, sync);

      checkResult(listFile, 0);
    } catch (IOException e) {
      LOG.error("Exception encountered while testing build listing", e);
      Assert.fail("build listing failure");
    } finally {
      TestDistCpUtils.delete(fs, "/tmp");
    }
  }

};