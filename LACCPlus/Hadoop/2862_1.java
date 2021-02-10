//,temp,TestFileBasedCopyListing.java,226,246,temp,TestIntegration.java,94,109
//,3
public class xxx {
  @Test
  public void testUpdateSingleDirTargetPresent() {

    try {
      Path listFile = new Path("/tmp/listing");
      Path target = new Path("/tmp/target");

      addEntries(listFile, "/tmp/Usingledir");
      mkdirs("/tmp/Usingledir/Udir1");
      mkdirs(target.toString());

      runTest(listFile, target, true, true);

      checkResult(listFile, 1);
    } catch (IOException e) {
      LOG.error("Exception encountered while testing build listing", e);
      Assert.fail("build listing failure");
    } finally {
      TestDistCpUtils.delete(fs, "/tmp");
    }
  }

};