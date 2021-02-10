//,temp,TestFileBasedCopyListing.java,301,321,temp,TestIntegration.java,201,218
//,3
public class xxx {
  @Test
  public void testMultiDirTargetPresent() {

    try {
      Path listFile = new Path("/tmp/listing");
      Path target = new Path("/tmp/target");

      addEntries(listFile, "/tmp/multifile", "/tmp/singledir");
      createFiles("/tmp/multifile/file3", "/tmp/multifile/file4", "/tmp/multifile/file5");
      mkdirs(target.toString(), "/tmp/singledir/dir1");

      runTest(listFile, target, true);

      checkResult(listFile, 4);
    } catch (IOException e) {
      LOG.error("Exception encountered while testing build listing", e);
      Assert.fail("build listing failure");
    } finally {
      TestDistCpUtils.delete(fs, "/tmp");
    }
  }

};