//,temp,TestQuotaByStorageType.java,395,418,temp,TestQuotaByStorageType.java,329,352
//,3
public class xxx {
  @Test(timeout = 60000)
  public void testQuotaByStorageTypeParentOffChildOn() throws Exception {
    final Path parent = new Path(dir, "parent");
    final Path child = new Path(parent, "child");
    dfs.mkdirs(parent);
    dfs.mkdirs(child);

    dfs.setStoragePolicy(parent, HdfsConstants.ONESSD_STORAGE_POLICY_NAME);
    dfs.setQuotaByStorageType(child, StorageType.SSD, 2 * BLOCKSIZE);

    // Create file of size 2.5 * BLOCKSIZE under child directory
    // Since child directory have SSD quota of 2 * BLOCKSIZE,
    // expect an exception when creating files under child directory.
    Path createdFile1 = new Path(child, "created_file1.data");
    long file1Len = BLOCKSIZE * 2 + BLOCKSIZE / 2;
    int bufLen = BLOCKSIZE / 16;
    try {
      DFSTestUtil.createFile(dfs, createdFile1, bufLen, file1Len, BLOCKSIZE,
          REPLICATION, seed);
      fail("Should have failed with QuotaByStorageTypeExceededException ");
    } catch (Throwable t) {
      LOG.info("Got expected exception ", t);
    }
  }

};