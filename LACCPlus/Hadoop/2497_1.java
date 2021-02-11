//,temp,TestBlockScanner.java,828,880,temp,TestBlockScanner.java,359,421
//,3
public class xxx {
  @Test(timeout=120000)
  public void testIgnoreMisplacedBlock() throws Exception {
    Configuration conf = new Configuration();
    // Set a really long scan period.
    conf.setLong(DFS_DATANODE_SCAN_PERIOD_HOURS_KEY, 100L);
    conf.set(INTERNAL_VOLUME_SCANNER_SCAN_RESULT_HANDLER,
        TestScanResultHandler.class.getName());
    conf.setLong(INTERNAL_DFS_BLOCK_SCANNER_CURSOR_SAVE_INTERVAL_MS, 0L);
    final TestContext ctx = new TestContext(conf, 1);
    final int NUM_FILES = 4;
    ctx.createFiles(0, NUM_FILES, 5);
    MaterializedReplica unreachableReplica = ctx.getMaterializedReplica(0, 1);
    ExtendedBlock unreachableBlock = ctx.getFileBlock(0, 1);
    unreachableReplica.makeUnreachable();
    final TestScanResultHandler.Info info =
        TestScanResultHandler.getInfo(ctx.volumes.get(0));
    String storageID = ctx.volumes.get(0).getStorageID();
    synchronized (info) {
      info.sem = new Semaphore(NUM_FILES);
      info.shouldRun = true;
      info.notify();
    }
    // Scan the first 4 blocks
    LOG.info("Waiting for the blocks to be scanned.");
    GenericTestUtils.waitFor(new Supplier<Boolean>() {
      @Override
      public Boolean get() {
        synchronized (info) {
          if (info.blocksScanned >= NUM_FILES - 1) {
            LOG.info("info = {}.  blockScanned has now reached " +
                info.blocksScanned, info);
            return true;
          } else {
            LOG.info("info = {}.  Waiting for blockScanned to reach " +
                (NUM_FILES - 1), info);
            return false;
          }
        }
      }
    }, 50, 30000);
    // We should have scanned 4 blocks
    synchronized (info) {
      assertFalse(info.goodBlocks.contains(unreachableBlock));
      assertFalse(info.badBlocks.contains(unreachableBlock));
      assertEquals("Expected 3 good blocks.", 3, info.goodBlocks.size());
      info.goodBlocks.clear();
      assertEquals("Expected 3 blocksScanned", 3, info.blocksScanned);
      assertEquals("Did not expect bad blocks.", 0, info.badBlocks.size());
      info.blocksScanned = 0;
    }
    info.sem.release(1);
    ctx.close();
  }

};