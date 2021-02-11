//,temp,TestShortCircuitCache.java,552,640,temp,TestShortCircuitCache.java,423,491
//,3
public class xxx {
  @Test(timeout=60000)
  public void testAllocShm() throws Exception {
    BlockReaderTestUtil.enableShortCircuitShmTracing();
    TemporarySocketDirectory sockDir = new TemporarySocketDirectory();
    Configuration conf = createShortCircuitConf("testAllocShm", sockDir);
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
        // The ClientShmManager starts off empty
        Assert.assertEquals(0,  info.size());
      }
    });
    DomainPeer peer = getDomainPeerToDn(conf);
    MutableBoolean usedPeer = new MutableBoolean(false);
    ExtendedBlockId blockId = new ExtendedBlockId(123, "xyz");
    final DatanodeInfo datanode = new DatanodeInfoBuilder()
        .setNodeID(cluster.getDataNodes().get(0).getDatanodeId())
        .build();
    // Allocating the first shm slot requires using up a peer.
    Slot slot = cache.allocShmSlot(datanode, peer, usedPeer,
                    blockId, "testAllocShm_client");
    Assert.assertNotNull(slot);
    Assert.assertTrue(usedPeer.booleanValue());
    cache.getDfsClientShmManager().visit(new Visitor() {
      @Override
      public void visit(HashMap<DatanodeInfo, PerDatanodeVisitorInfo> info)
          throws IOException {
        // The ClientShmManager starts off empty
        Assert.assertEquals(1,  info.size());
        PerDatanodeVisitorInfo vinfo = info.get(datanode);
        Assert.assertFalse(vinfo.disabled);
        Assert.assertEquals(0, vinfo.full.size());
        Assert.assertEquals(1, vinfo.notFull.size());
      }
    });
    cache.scheduleSlotReleaser(slot);
    // Wait for the slot to be released, and the shared memory area to be
    // closed.  Since we didn't register this shared memory segment on the
    // server, it will also be a test of how well the server deals with
    // bogus client behavior.
    GenericTestUtils.waitFor(new Supplier<Boolean>() {
      @Override
      public Boolean get() {
        final MutableBoolean done = new MutableBoolean(false);
        try {
          cache.getDfsClientShmManager().visit(new Visitor() {
            @Override
            public void visit(HashMap<DatanodeInfo, PerDatanodeVisitorInfo> info)
                throws IOException {
              done.setValue(info.get(datanode).full.isEmpty() &&
                  info.get(datanode).notFull.isEmpty());
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