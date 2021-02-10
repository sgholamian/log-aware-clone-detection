//,temp,TestQuotaByStorageType.java,257,301,temp,TestQuotaByStorageType.java,207,251
//,3
public class xxx {
  @Test(timeout = 60000)
  public void testQuotaByStorageTypeExceptionWithFileCreate() throws Exception {
    final Path foo = new Path(dir, "foo");
    Path createdFile1 = new Path(foo, "created_file1.data");
    dfs.mkdirs(foo);

    dfs.setStoragePolicy(foo, HdfsConstants.ONESSD_STORAGE_POLICY_NAME);
    dfs.setQuotaByStorageType(foo, StorageType.SSD, BLOCKSIZE * 4);

    INode fnode = fsdir.getINode4Write(foo.toString());
    assertTrue(fnode.isDirectory());
    assertTrue(fnode.isQuotaSet());

    // Create the 1st file of size 2 * BLOCKSIZE under directory "foo" and expect no exception
    long file1Len = BLOCKSIZE * 2;
    int bufLen = BLOCKSIZE / 16;
    DFSTestUtil.createFile(dfs, createdFile1, bufLen, file1Len, BLOCKSIZE, REPLICATION, seed);
    long currentSSDConsumed = fnode.asDirectory().getDirectoryWithQuotaFeature()
        .getSpaceConsumed().getTypeSpaces().get(StorageType.SSD);
    assertEquals(file1Len, currentSSDConsumed);

    // Create the 2nd file of size 1.5 * BLOCKSIZE under directory "foo" and expect no exception
    Path createdFile2 = new Path(foo, "created_file2.data");
    long file2Len = BLOCKSIZE + BLOCKSIZE / 2;
    DFSTestUtil.createFile(dfs, createdFile2, bufLen, file2Len, BLOCKSIZE, REPLICATION, seed);
    currentSSDConsumed = fnode.asDirectory().getDirectoryWithQuotaFeature()
        .getSpaceConsumed().getTypeSpaces().get(StorageType.SSD);

    assertEquals(file1Len + file2Len, currentSSDConsumed);

    // Create the 3rd file of size BLOCKSIZE under directory "foo" and expect quota exceeded exception
    Path createdFile3 = new Path(foo, "created_file3.data");
    long file3Len = BLOCKSIZE;

    try {
      DFSTestUtil.createFile(dfs, createdFile3, bufLen, file3Len, BLOCKSIZE, REPLICATION, seed);
      fail("Should have failed with QuotaByStorageTypeExceededException ");
    } catch (Throwable t) {
      LOG.info("Got expected exception ", t);

      currentSSDConsumed = fnode.asDirectory().getDirectoryWithQuotaFeature()
          .getSpaceConsumed().getTypeSpaces().get(StorageType.SSD);
      assertEquals(file1Len + file2Len, currentSSDConsumed);
    }
  }

};