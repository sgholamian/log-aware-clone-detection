//,temp,TestQuotaByStorageType.java,863,900,temp,TestQuotaByStorageType.java,804,857
//,3
public class xxx {
  @Test(timeout = 30000)
  public void testStorageSpaceQuotaWithRepFactor() throws IOException {
    final Path testDir = new Path(dir,
        GenericTestUtils.getMethodName());
    assertTrue(dfs.mkdirs(testDir));

    final long storageSpaceQuota = BLOCKSIZE * 2;

    /* set policy to HOT */
    dfs.setStoragePolicy(testDir, HdfsConstants.HOT_STORAGE_POLICY_NAME);

    /* set space quota */
    dfs.setQuota(testDir, HdfsConstants.QUOTA_DONT_SET, storageSpaceQuota);

    /* init vars */
    Path createdFile = null;
    final long fileLen = BLOCKSIZE;

    try {
      /* create one file with 3 replicas */
      createdFile = new Path(testDir, "file1.data");
      DFSTestUtil.createFile(dfs, createdFile, BLOCKSIZE / 16, fileLen,
          BLOCKSIZE, REPLICATION, seed);
      fail("should fail on DSQuotaExceededException");
    } catch (DSQuotaExceededException e) {
      LOG.info("Got expected exception ", e);
      assertThat(e.toString(),
          is(allOf(containsString("DiskSpace quota"),
              containsString(testDir.toString()))));
    }

    /* try creating file again with 2 replicas */
    createdFile = new Path(testDir, "file2.data");
    DFSTestUtil.createFile(dfs, createdFile, BLOCKSIZE / 16, fileLen, BLOCKSIZE,
        (short) 2, seed);
    assertTrue(dfs.exists(createdFile));
    assertTrue(dfs.isFile(createdFile));
  }

};