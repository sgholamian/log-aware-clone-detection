//,temp,TestFileBasedCopyListing.java,429,452,temp,TestIntegration.java,344,362
//,3
public class xxx {
  @Test
  public void testGlobTargetDirMultiLevel() {

    try {
      Path listFile = new Path("/tmp1/listing");
      Path target = new Path("/tmp/target");

      addEntries(listFile, "/tmp/*/*");
      createFiles("/tmp/multifile/file3", "/tmp/multifile/file4", "/tmp/multifile/file5");
      createFiles("/tmp/singledir1/dir3/file7", "/tmp/singledir1/dir3/file8",
          "/tmp/singledir1/dir3/file9");
      mkdirs(target.toString());

      runTest(listFile, target, true);

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