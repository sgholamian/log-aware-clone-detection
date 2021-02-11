//,temp,TestDistCpViewFs.java,188,205,temp,TestFileBasedCopyListing.java,131,149
//,3
public class xxx {
  private void caseSingleFileTargetFile(boolean sync) {

    try {
      Path listFile = new Path("/tmp/listing");
      Path target = new Path("/tmp/target");

      addEntries(listFile, "/tmp/singlefile1/file1");
      createFiles("/tmp/singlefile1/file1", target.toString());

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