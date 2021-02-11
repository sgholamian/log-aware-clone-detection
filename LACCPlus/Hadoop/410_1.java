//,temp,TestQuotaByStorageType.java,497,531,temp,TestQuotaByStorageType.java,257,301
//,3
public class xxx {
  private void testQuotaByStorageTypeOrTraditionalQuotaExceededCase(
      long storageSpaceQuotaInBlocks, long ssdQuotaInBlocks,
      long testFileLenInBlocks, short replication) throws Exception {
    final String METHOD_NAME = GenericTestUtils.getMethodName();
    final Path testDir = new Path(dir, METHOD_NAME);

    dfs.mkdirs(testDir);
    dfs.setStoragePolicy(testDir, HdfsConstants.ONESSD_STORAGE_POLICY_NAME);

    final long ssdQuota = BLOCKSIZE * ssdQuotaInBlocks;
    final long storageSpaceQuota = BLOCKSIZE * storageSpaceQuotaInBlocks;

    dfs.setQuota(testDir, Long.MAX_VALUE - 1, storageSpaceQuota);
    dfs.setQuotaByStorageType(testDir, StorageType.SSD, ssdQuota);

    INode testDirNode = fsdir.getINode4Write(testDir.toString());
    assertTrue(testDirNode.isDirectory());
    assertTrue(testDirNode.isQuotaSet());

    Path createdFile = new Path(testDir, "created_file.data");
    long fileLen = testFileLenInBlocks * BLOCKSIZE;

    try {
      DFSTestUtil.createFile(dfs, createdFile, BLOCKSIZE / 16,
          fileLen, BLOCKSIZE, replication, seed);
      fail("Should have failed with DSQuotaExceededException or " +
          "QuotaByStorageTypeExceededException ");
    } catch (Throwable t) {
      LOG.info("Got expected exception ", t);
      long currentSSDConsumed = testDirNode.asDirectory().getDirectoryWithQuotaFeature()
          .getSpaceConsumed().getTypeSpaces().get(StorageType.SSD);
      assertEquals(Math.min(ssdQuota, storageSpaceQuota/replication),
          currentSSDConsumed);
    }
  }

};