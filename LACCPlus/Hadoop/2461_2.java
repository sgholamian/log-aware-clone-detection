//,temp,TestQuotaByStorageType.java,365,404,temp,TestQuotaByStorageType.java,218,262
//,3
public class xxx {
  @Test(timeout = 60000)
  public void testQuotaByStorageTypeWithFileCreateRename() throws Exception {
    final Path foo = new Path(dir, "foo");
    dfs.mkdirs(foo);
    Path createdFile1foo = new Path(foo, "created_file1.data");

    final Path bar = new Path(dir, "bar");
    dfs.mkdirs(bar);
    Path createdFile1bar = new Path(bar, "created_file1.data");

    // set storage policy on directory "foo" and "bar" to ONESSD
    dfs.setStoragePolicy(foo, HdfsConstants.ONESSD_STORAGE_POLICY_NAME);
    dfs.setStoragePolicy(bar, HdfsConstants.ONESSD_STORAGE_POLICY_NAME);

    // set quota by storage type on directory "foo"
    dfs.setQuotaByStorageType(foo, StorageType.SSD, BLOCKSIZE * 4);
    dfs.setQuotaByStorageType(bar, StorageType.SSD, BLOCKSIZE * 2);

    INode fnode = fsdir.getINode4Write(foo.toString());
    assertTrue(fnode.isDirectory());
    assertTrue(fnode.isQuotaSet());

    // Create file of size 3 * BLOCKSIZE under directory "foo"
    long file1Len = BLOCKSIZE * 3;
    int bufLen = BLOCKSIZE / 16;
    DFSTestUtil.createFile(dfs, createdFile1foo, bufLen, file1Len, BLOCKSIZE, REPLICATION, seed);

    // Verify space consumed and remaining quota
    long ssdConsumed = fnode.asDirectory().getDirectoryWithQuotaFeature()
        .getSpaceConsumed().getTypeSpaces().get(StorageType.SSD);
    assertEquals(file1Len, ssdConsumed);

    // move file from foo to bar
    try {
      dfs.rename(createdFile1foo, createdFile1bar);
      fail("Should have failed with QuotaByStorageTypeExceededException ");
    } catch (Throwable t) {
      LOG.info("Got expected exception ", t);
    }

    ContentSummary cs = dfs.getContentSummary(foo);
    assertEquals(cs.getSpaceConsumed(), file1Len * REPLICATION);
    assertEquals(cs.getTypeConsumed(StorageType.SSD), file1Len);
    assertEquals(cs.getTypeConsumed(StorageType.DISK), file1Len * 2);
  }

};