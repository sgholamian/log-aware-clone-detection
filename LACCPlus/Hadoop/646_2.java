//,temp,TestDistCpViewFs.java,82,97,temp,TestFileBasedCopyListing.java,204,224
//,3
public class xxx {
  @Test
  public void testSingleDirTargetPresent() {

    try {
      Path listFile = new Path("/tmp/listing");
      Path target = new Path("/tmp/target");

      addEntries(listFile, "/tmp/singledir");
      mkdirs("/tmp/singledir/dir1");
      mkdirs(target.toString());

      runTest(listFile, target, true);

      checkResult(listFile, 1);
    } catch (IOException e) {
      LOG.error("Exception encountered while testing build listing", e);
      Assert.fail("build listing failure");
    } finally {
      TestDistCpUtils.delete(fs, "/tmp");
    }
  }

};