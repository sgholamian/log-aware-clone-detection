//,temp,TestBlockScanner.java,605,659,temp,TestBlockScanner.java,345,407
//,3
public class xxx {
  @Test(timeout=120000)
  public void testMultipleBlockPoolScanning() throws Exception {
    Configuration conf = new Configuration();
    conf.setLong(DFS_DATANODE_SCAN_PERIOD_HOURS_KEY, 100L);
    conf.set(INTERNAL_VOLUME_SCANNER_SCAN_RESULT_HANDLER,
        TestScanResultHandler.class.getName());
    final TestContext ctx = new TestContext(conf, 3);

    // We scan 5 bytes per file (1 byte in file, 4 bytes of checksum)
    final int BYTES_SCANNED_PER_FILE = 5;
    final int NUM_FILES[] = new int[] { 1, 5, 10 };
    int TOTAL_FILES = 0;
    for (int i = 0; i < NUM_FILES.length; i++) {
      TOTAL_FILES += NUM_FILES[i];
    }
    ctx.createFiles(0, NUM_FILES[0], 1);
    ctx.createFiles(0, NUM_FILES[1], 1);
    ctx.createFiles(0, NUM_FILES[2], 1);

    // start scanning
    final TestScanResultHandler.Info info =
        TestScanResultHandler.getInfo(ctx.volumes.get(0));
    synchronized (info) {
      info.shouldRun = true;
      info.notify();
    }

    // Wait for all the block pools to be scanned.
    GenericTestUtils.waitFor(new Supplier<Boolean>() {
      @Override
      public Boolean get() {
        synchronized (info) {
          Statistics stats = ctx.blockScanner.getVolumeStats(
              ctx.volumes.get(0).getStorageID());
          if (stats.scansSinceRestart < 3) {
            LOG.info("Waiting for scansSinceRestart to reach 3 (it is {})",
                stats.scansSinceRestart);
            return false;
          }
          if (!stats.eof) {
            LOG.info("Waiting for eof.");
            return false;
          }
          return true;
        }
      }
    }, 3, 30000);

    Statistics stats = ctx.blockScanner.getVolumeStats(
        ctx.volumes.get(0).getStorageID());
    assertEquals(TOTAL_FILES, stats.blocksScannedSinceRestart);
    assertEquals(BYTES_SCANNED_PER_FILE * TOTAL_FILES,
        stats.bytesScannedInPastHour);
    ctx.close();
  }

};