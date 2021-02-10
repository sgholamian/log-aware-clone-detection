//,temp,TestFileBasedCopyListing.java,184,202,temp,TestIntegration.java,141,157
//,3
public class xxx {
  private void caseSingleDirTargetMissing(boolean sync) {

    try {
      Path listFile = new Path("/tmp/listing");
      Path target = new Path("/tmp/target");

      addEntries(listFile, "/tmp/singledir");
      mkdirs("/tmp/singledir/dir1");

      runTest(listFile, target, false, sync);

      checkResult(listFile, 1);
    } catch (IOException e) {
      LOG.error("Exception encountered while testing build listing", e);
      Assert.fail("build listing failure");
    } finally {
      TestDistCpUtils.delete(fs, "/tmp");
    }
  }

};