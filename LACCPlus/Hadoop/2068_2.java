//,temp,TestBlockScanner.java,605,659,temp,TestBlockScanner.java,345,407
//,3
public class xxx {
  private void testScanAllBlocksImpl(final boolean rescan) throws Exception {
    Configuration conf = new Configuration();
    conf.setLong(DFS_BLOCK_SCANNER_VOLUME_BYTES_PER_SECOND, 1048576L);
    if (rescan) {
      conf.setLong(INTERNAL_DFS_DATANODE_SCAN_PERIOD_MS, 100L);
    } else {
      conf.setLong(DFS_DATANODE_SCAN_PERIOD_HOURS_KEY, 100L);
    }
    conf.set(INTERNAL_VOLUME_SCANNER_SCAN_RESULT_HANDLER,
        TestScanResultHandler.class.getName());
    final TestContext ctx = new TestContext(conf, 1);
    final int NUM_EXPECTED_BLOCKS = 10;
    ctx.createFiles(0, NUM_EXPECTED_BLOCKS, 1);
    final Set<ExtendedBlock> expectedBlocks = new HashSet<ExtendedBlock>();
    for (int i = 0; i < NUM_EXPECTED_BLOCKS; i++) {
      expectedBlocks.add(ctx.getFileBlock(0, i));
    }
    TestScanResultHandler.Info info =
        TestScanResultHandler.getInfo(ctx.volumes.get(0));
    synchronized (info) {
      info.shouldRun = true;
      info.notify();
    }
    GenericTestUtils.waitFor(new Supplier<Boolean>(){
      @Override
      public Boolean get() {
        TestScanResultHandler.Info info =
            TestScanResultHandler.getInfo(ctx.volumes.get(0));
        int numFoundBlocks = 0;
        StringBuilder foundBlocksBld = new StringBuilder();
        String prefix = "";
        synchronized (info) {
          for (ExtendedBlock block : info.goodBlocks) {
            assertTrue(expectedBlocks.contains(block));
            numFoundBlocks++;
            foundBlocksBld.append(prefix).append(block);
            prefix = ", ";
          }
          LOG.info("numFoundBlocks = {}.  blocksScanned = {}. Found blocks {}",
              numFoundBlocks, info.blocksScanned, foundBlocksBld.toString());
          if (rescan) {
            return (numFoundBlocks == NUM_EXPECTED_BLOCKS) &&
                     (info.blocksScanned >= 2 * NUM_EXPECTED_BLOCKS);
          } else {
            return numFoundBlocks == NUM_EXPECTED_BLOCKS;
          }
        }
      }
    }, 10, 60000);
    if (!rescan) {
      synchronized (info) {
        assertEquals(NUM_EXPECTED_BLOCKS, info.blocksScanned);
      }
      Statistics stats = ctx.blockScanner.getVolumeStats(
          ctx.volumes.get(0).getStorageID());
      assertEquals(5 * NUM_EXPECTED_BLOCKS, stats.bytesScannedInPastHour);
      assertEquals(NUM_EXPECTED_BLOCKS, stats.blocksScannedSinceRestart);
      assertEquals(NUM_EXPECTED_BLOCKS, stats.blocksScannedInCurrentPeriod);
      assertEquals(0, stats.scanErrorsSinceRestart);
      assertEquals(1, stats.scansSinceRestart);
    }
    ctx.close();
  }

};