//,temp,TestShortCircuitCache.java,552,640,temp,TestShortCircuitCache.java,423,491
//,3
public class xxx {
  @Test(timeout=60000)
  public void testUnlinkingReplicasInFileDescriptorCache() throws Exception {
    BlockReaderTestUtil.enableShortCircuitShmTracing();
    TemporarySocketDirectory sockDir = new TemporarySocketDirectory();
    Configuration conf = createShortCircuitConf(
        "testUnlinkingReplicasInFileDescriptorCache", sockDir);
    // We don't want the CacheCleaner to time out short-circuit shared memory
    // segments during the test, so set the timeout really high.
    conf.setLong(HdfsClientConfigKeys.Read.ShortCircuit.STREAMS_CACHE_EXPIRY_MS_KEY,
        1000000000L);
    MiniDFSCluster cluster =
        new MiniDFSCluster.Builder(conf).numDataNodes(1).build();
    cluster.waitActive();
    DistributedFileSystem fs = cluster.getFileSystem();
    final ShortCircuitCache cache =
        fs.getClient().getClientContext().getShortCircuitCache();
    cache.getDfsClientShmManager().visit(new Visitor() {
      @Override
      public void visit(HashMap<DatanodeInfo, PerDatanodeVisitorInfo> info)
          throws IOException {
        // The ClientShmManager starts off empty.
        Assert.assertEquals(0,  info.size());
      }
    });
    final Path TEST_PATH = new Path("/test_file");
    final int TEST_FILE_LEN = 8193;
    final int SEED = 0xFADE0;
    DFSTestUtil.createFile(fs, TEST_PATH, TEST_FILE_LEN,
        (short)1, SEED);
    byte contents[] = DFSTestUtil.readFileBuffer(fs, TEST_PATH);
    byte expected[] = DFSTestUtil.
        calculateFileContentsFromSeed(SEED, TEST_FILE_LEN);
    Assert.assertTrue(Arrays.equals(contents, expected));
    // Loading this file brought the ShortCircuitReplica into our local
    // replica cache.
    final DatanodeInfo datanode = new DatanodeInfoBuilder()
        .setNodeID(cluster.getDataNodes().get(0).getDatanodeId())
        .build();
    cache.getDfsClientShmManager().visit(new Visitor() {
      @Override
      public void visit(HashMap<DatanodeInfo, PerDatanodeVisitorInfo> info)
          throws IOException {
        Assert.assertTrue(info.get(datanode).full.isEmpty());
        Assert.assertFalse(info.get(datanode).disabled);
        Assert.assertEquals(1, info.get(datanode).notFull.values().size());
        DfsClientShm shm =
            info.get(datanode).notFull.values().iterator().next();
        Assert.assertFalse(shm.isDisconnected());
      }
    });
    // Remove the file whose blocks we just read.
    fs.delete(TEST_PATH, false);

    // Wait for the replica to be purged from the DFSClient's cache.
    GenericTestUtils.waitFor(new Supplier<Boolean>() {
      MutableBoolean done = new MutableBoolean(true);
      @Override
      public Boolean get() {
        try {
          done.setValue(true);
          cache.getDfsClientShmManager().visit(new Visitor() {
            @Override
            public void visit(HashMap<DatanodeInfo,
                  PerDatanodeVisitorInfo> info) throws IOException {
              Assert.assertTrue(info.get(datanode).full.isEmpty());
              Assert.assertFalse(info.get(datanode).disabled);
              Assert.assertEquals(1,
                  info.get(datanode).notFull.values().size());
              DfsClientShm shm = info.get(datanode).notFull.values().
                  iterator().next();
              // Check that all slots have been invalidated.
              for (Iterator<Slot> iter = shm.slotIterator();
                   iter.hasNext(); ) {
                Slot slot = iter.next();
                if (slot.isValid()) {
                  done.setValue(false);
                }
              }
            }
          });
        } catch (IOException e) {
          LOG.error("error running visitor", e);
        }
        return done.booleanValue();
      }
    }, 10, 60000);
    cluster.shutdown();
    sockDir.close();
  }

};