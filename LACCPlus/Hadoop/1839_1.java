//,temp,TestFileBasedCopyListing.java,406,427,temp,TestIntegration.java,419,439
//,3
public class xxx {
  private void caseGlobTargetMissingMultiLevel(boolean sync) {

    try {
      Path listFile = new Path("/tmp1/listing");
      Path target = new Path("/tmp/target");

      addEntries(listFile, "/tmp/*/*");
      createFiles("/tmp/multifile/file3", "/tmp/multifile/file4", "/tmp/multifile/file5");
      createFiles("/tmp/singledir1/dir3/file7", "/tmp/singledir1/dir3/file8",
          "/tmp/singledir1/dir3/file9");

      runTest(listFile, target, sync);

      checkResult(listFile, 6);
    } catch (IOException e) {
      LOG.error("Exception encountered while testing build listing", e);
      Assert.fail("build listing failure");
    } finally {
      TestDistCpUtils.delete(fs, "/tmp");
      TestDistCpUtils.delete(fs, "/tmp1");
    }
  }

};