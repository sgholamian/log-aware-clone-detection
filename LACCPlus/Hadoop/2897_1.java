//,temp,TestFileBasedCopyListing.java,454,477,temp,TestIntegration.java,418,438
//,3
public class xxx {
  @Test
  public void testUpdateGlobTargetDirMultiLevel() {

    try {
      Path listFile = new Path("/tmp1/listing");
      Path target = new Path("/tmp/target");

      addEntries(listFile, "/tmp/*/*");
      createFiles("/tmp/Umultifile/Ufile3", "/tmp/Umultifile/Ufile4", "/tmp/Umultifile/Ufile5");
      createFiles("/tmp/Usingledir1/Udir3/Ufile7", "/tmp/Usingledir1/Udir3/Ufile8",
          "/tmp/Usingledir1/Udir3/Ufile9");
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