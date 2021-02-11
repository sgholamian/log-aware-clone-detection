//,temp,TestQuotaByStorageType.java,863,900,temp,TestQuotaByStorageType.java,804,857
//,3
public class xxx {
  @Test
  public void testStorageSpaceQuotaWithWarmPolicy() throws IOException {
    final Path testDir = new Path(dir,
        GenericTestUtils.getMethodName());
    assertTrue(dfs.mkdirs(testDir));

    /* set policy to HOT */
    dfs.setStoragePolicy(testDir, HdfsConstants.HOT_STORAGE_POLICY_NAME);

    /* init space quota */
    final long storageSpaceQuota = BLOCKSIZE * 6;
    final long storageTypeSpaceQuota = BLOCKSIZE * 1;

    /* set space quota */
    dfs.setQuota(testDir, HdfsConstants.QUOTA_DONT_SET, storageSpaceQuota);

    /* init vars */
    Path createdFile;
    final long fileLen = BLOCKSIZE;

    /**
     * create one file with 3 replicas, REPLICATION * BLOCKSIZE go to DISK due
     * to HOT policy
     */
    createdFile = new Path(testDir, "file1.data");
    DFSTestUtil.createFile(dfs, createdFile, BLOCKSIZE / 16, fileLen, BLOCKSIZE,
        REPLICATION, seed);
    assertTrue(dfs.exists(createdFile));
    assertTrue(dfs.isFile(createdFile));

    /* set space quota for DISK */
    dfs.setQuotaByStorageType(testDir, StorageType.DISK, storageTypeSpaceQuota);

    /* set policy to WARM */
    dfs.setStoragePolicy(testDir, HdfsConstants.WARM_STORAGE_POLICY_NAME);

    /* create another file with 3 replicas */
    try {
      createdFile = new Path(testDir, "file2.data");
      /**
       * This will fail since quota on DISK is 1 block but space consumed on
       * DISK is already 3 blocks due to the first file creation.
       */
      DFSTestUtil.createFile(dfs, createdFile, BLOCKSIZE / 16, fileLen,
          BLOCKSIZE, REPLICATION, seed);
      fail("should fail on QuotaByStorageTypeExceededException");
    } catch (QuotaByStorageTypeExceededException e) {
      LOG.info("Got expected exception ", e);
      assertThat(e.toString(),
          is(allOf(containsString("Quota by storage type"),
              containsString("DISK on path"),
              containsString(testDir.toString()))));
    }
  }

};