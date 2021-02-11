//,temp,TestFileBasedCopyListing.java,323,343,temp,TestIntegration.java,250,265
//,3
public class xxx {
  @Test
  public void testUpdateMultiDirTargetPresent() {

    try {
      Path listFile = new Path("/tmp/listing");
      Path target = new Path("/tmp/target");

      addEntries(listFile, "/tmp/Umultifile", "/tmp/Usingledir");
      createFiles("/tmp/Umultifile/Ufile3", "/tmp/Umultifile/Ufile4", "/tmp/Umultifile/Ufile5");
      mkdirs(target.toString(), "/tmp/Usingledir/Udir1");

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