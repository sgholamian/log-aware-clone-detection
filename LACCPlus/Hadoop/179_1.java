//,temp,TestFileBasedCopyListing.java,281,299,temp,TestIntegration.java,396,417
//,3
public class xxx {
  private void caseMultiFileTargetMissing(boolean sync) {

    try {
      Path listFile = new Path("/tmp/listing");
      Path target = new Path("/tmp/target");

      addEntries(listFile, "/tmp/multifile/file3", "/tmp/multifile/file4", "/tmp/multifile/file5");
      createFiles("/tmp/multifile/file3", "/tmp/multifile/file4", "/tmp/multifile/file5");

      runTest(listFile, target, false, sync);

      checkResult(listFile, 3);
    } catch (IOException e) {
      LOG.error("Exception encountered while testing build listing", e);
      Assert.fail("build listing failure");
    } finally {
      TestDistCpUtils.delete(fs, "/tmp");
    }
  }

};