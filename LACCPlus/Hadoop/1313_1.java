//,temp,TestFileBasedCopyListing.java,157,176,temp,TestIntegration.java,118,133
//,3
public class xxx {
  private void caseSingleFileTargetDir(boolean sync) {

    try {
      Path listFile = new Path("/tmp/listing");
      Path target = new Path("/tmp/target");

      addEntries(listFile, "/tmp/singlefile2/file2");
      createFiles("/tmp/singlefile2/file2");
      mkdirs(target.toString());

      runTest(listFile, target, true, sync);

      checkResult(listFile, 1);
    } catch (IOException e) {
      LOG.error("Exception encountered while testing build listing", e);
      Assert.fail("build listing failure");
    } finally {
      TestDistCpUtils.delete(fs, "/tmp");
    }
  }

};