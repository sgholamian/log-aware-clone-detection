//,temp,TestDistCpViewFs.java,331,352,temp,TestFileBasedCopyListing.java,254,273
//,3
public class xxx {
  private void caseMultiFileTargetPresent(boolean sync) {

    try {
      Path listFile = new Path("/tmp/listing");
      Path target = new Path("/tmp/target");

      addEntries(listFile, "/tmp/multifile/file3", "/tmp/multifile/file4", "/tmp/multifile/file5");
      createFiles("/tmp/multifile/file3", "/tmp/multifile/file4", "/tmp/multifile/file5");
      mkdirs(target.toString());

      runTest(listFile, target, true, sync);

      checkResult(listFile, 3);
    } catch (IOException e) {
      LOG.error("Exception encountered while testing build listing", e);
      Assert.fail("build listing failure");
    } finally {
      TestDistCpUtils.delete(fs, "/tmp");
    }
  }

};