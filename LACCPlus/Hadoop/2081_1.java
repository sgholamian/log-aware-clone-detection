//,temp,TestQuotaByStorageType.java,354,393,temp,TestQuotaByStorageType.java,257,301
//,3
public class xxx {
  @Test(timeout = 60000)
  public void testQuotaByStorageTypeParentOnChildOff() throws Exception {
    short replication = 1;
    final Path parent = new Path(dir, "parent");
    final Path child = new Path(parent, "child");
    dfs.mkdirs(parent);
    dfs.mkdirs(child);

    dfs.setStoragePolicy(parent, HdfsConstants.ONESSD_STORAGE_POLICY_NAME);
    dfs.setQuotaByStorageType(parent, StorageType.SSD, 3 * BLOCKSIZE);

    // Create file of size 2.5 * BLOCKSIZE under child directory
    // Verify parent Quota applies
    Path createdFile1 = new Path(child, "created_file1.data");
    long file1Len = BLOCKSIZE * 2 + BLOCKSIZE / 2;
    int bufLen = BLOCKSIZE / 16;
    DFSTestUtil.createFile(dfs, createdFile1, bufLen, file1Len, BLOCKSIZE,
        replication, seed);

    INode fnode = fsdir.getINode4Write(parent.toString());
    assertTrue(fnode.isDirectory());
    assertTrue(fnode.isQuotaSet());
    long currentSSDConsumed = fnode.asDirectory().getDirectoryWithQuotaFeature()
        .getSpaceConsumed().getTypeSpaces().get(StorageType.SSD);
    assertEquals(file1Len, currentSSDConsumed);

    // Create the 2nd file of size BLOCKSIZE under child directory and expect quota exceeded exception
    Path createdFile2 = new Path(child, "created_file2.data");
    long file2Len = BLOCKSIZE;

    try {
      DFSTestUtil.createFile(dfs, createdFile2, bufLen, file2Len, BLOCKSIZE, replication, seed);
      fail("Should have failed with QuotaByStorageTypeExceededException ");
    } catch (Throwable t) {
      LOG.info("Got expected exception ", t);
      currentSSDConsumed = fnode.asDirectory().getDirectoryWithQuotaFeature()
          .getSpaceConsumed().getTypeSpaces().get(StorageType.SSD);
      assertEquals(file1Len, currentSSDConsumed);
    }
  }

};