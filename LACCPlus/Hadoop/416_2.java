//,temp,TestFiRename.java,225,258,temp,TestFiRename.java,185,222
//,3
public class xxx {
  @Test
  public void testDeletionOfDstFile() throws Exception {
    Path src = getTestPath("testDeletionOfDstFile/dir/src");
    Path dst = getTestPath("testDeletionOfDstFile/newdir/dst");
    createFile(src);
    createFile(dst);

    final FSNamesystem namesystem = cluster.getNamesystem();
    final long blocks = namesystem.getBlocksTotal();
    final long fileCount = namesystem.getFilesTotal();
    rename(src, dst, false, false, true, Rename.OVERWRITE);

    // After successful rename the blocks corresponing dst are deleted
    Assert.assertEquals(blocks - 1, namesystem.getBlocksTotal());

    // After successful rename dst file is deleted
    Assert.assertEquals(fileCount - 1, namesystem.getFilesTotal());

    // Restart the cluster to ensure new rename operation 
    // recorded in editlog is processed right
    restartCluster(false);
    int count = 0;
    boolean exception = true;
    src = getTestPath("testDeletionOfDstFile/dir/src");
    dst = getTestPath("testDeletionOfDstFile/newdir/dst");
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