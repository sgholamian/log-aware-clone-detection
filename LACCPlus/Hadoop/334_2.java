//,temp,TestDistCpViewFs.java,237,252,temp,TestFileBasedCopyListing.java,378,398
//,3
public class xxx {
  private void caseGlobTargetMissingSingleLevel(boolean sync) {

    try {
      Path listFile = new Path("/tmp1/listing");
      Path target = new Path("/tmp/target");

      addEntries(listFile, "/tmp/*");
      createFiles("/tmp/multifile/file3", "/tmp/multifile/file4", "/tmp/multifile/file5");
      createFiles("/tmp/singledir/dir2/file6");

      runTest(listFile, target, sync);

      checkResult(listFile, 5);
    } catch (IOException e) {
      LOG.error("Exception encountered while testing build listing", e);
      Assert.fail("build listing failure");
    } finally {
      TestDistCpUtils.delete(fs, "/tmp");
      TestDistCpUtils.delete(fs, "/tmp1");
    }
  }

};