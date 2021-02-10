//,temp,TestFiRename.java,225,258,temp,TestFiRename.java,185,222
//,3
public class xxx {
  @Test
  public void testDeletionOfDstDirectory() throws Exception {
    Path src = getTestPath("testDeletionOfDstDirectory/dir/src");
    Path dst = getTestPath("testDeletionOfDstDirectory/newdir/dst");
    fc.mkdir(src, FileContext.DEFAULT_PERM, true);
    fc.mkdir(dst, FileContext.DEFAULT_PERM, true);

    FSNamesystem namesystem = cluster.getNamesystem();
    long fileCount = namesystem.getFilesTotal();
    rename(src, dst, false, false, true, Rename.OVERWRITE);

    // After successful rename dst directory is deleted
    Assert.assertEquals(fileCount - 1, namesystem.getFilesTotal());
    
    // Restart the cluster to ensure new rename operation 
    // recorded in editlog is processed right
    restartCluster(false);
    src = getTestPath("testDeletionOfDstDirectory/dir/src");
    dst = getTestPath("testDeletionOfDstDirectory/newdir/dst");
    int count = 0;
    boolean exception = true;
    while (exception && count < 5) {
      try {
        exists(fc, src);
        exception = false;
      } catch (Exception e) {
        LOG.warn("Exception " + " count " + count + " " + e.getMessage());
        Thread.sleep(1000);
        count++;
      }
    }
    Assert.assertFalse(exists(fc, src));
    Assert.assertTrue(exists(fc, dst));
  }

};