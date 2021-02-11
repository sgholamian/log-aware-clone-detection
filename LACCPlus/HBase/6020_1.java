//,temp,TestHRegion.java,3562,3650,temp,TestHRegion.java,3431,3495
//,3
public class xxx {
  @Test
  public void testWritesWhileScanning() throws IOException, InterruptedException {
    int testCount = 100;
    int numRows = 1;
    int numFamilies = 10;
    int numQualifiers = 100;
    int flushInterval = 7;
    int compactInterval = 5 * flushInterval;
    byte[][] families = new byte[numFamilies][];
    for (int i = 0; i < numFamilies; i++) {
      families[i] = Bytes.toBytes("family" + i);
    }
    byte[][] qualifiers = new byte[numQualifiers][];
    for (int i = 0; i < numQualifiers; i++) {
      qualifiers[i] = Bytes.toBytes("qual" + i);
    }

    this.region = initHRegion(tableName, method, CONF, families);
    FlushThread flushThread = new FlushThread();
    PutThread putThread = new PutThread(numRows, families, qualifiers);
    try {
      putThread.start();
      putThread.waitForFirstPut();

      flushThread.start();

      Scan scan = new Scan(Bytes.toBytes("row0"), Bytes.toBytes("row1"));

      int expectedCount = numFamilies * numQualifiers;
      List<Cell> res = new ArrayList<>();

      long prevTimestamp = 0L;
      for (int i = 0; i < testCount; i++) {

        if (i != 0 && i % compactInterval == 0) {
          region.compact(true);
          for (HStore store : region.getStores()) {
            store.closeAndArchiveCompactedFiles();
          }
        }

        if (i != 0 && i % flushInterval == 0) {
          flushThread.flush();
        }

        boolean previousEmpty = res.isEmpty();
        res.clear();
        try (InternalScanner scanner = region.getScanner(scan)) {
          boolean moreRows;
          do {
            moreRows = scanner.next(res);
          } while (moreRows);
        }
        if (!res.isEmpty() || !previousEmpty || i > compactInterval) {
          assertEquals("i=" + i, expectedCount, res.size());
          long timestamp = res.get(0).getTimestamp();
          assertTrue("Timestamps were broke: " + timestamp + " prev: " + prevTimestamp,
              timestamp >= prevTimestamp);
          prevTimestamp = timestamp;
        }
      }

      putThread.done();

      region.flush(true);

    } finally {
      try {
        flushThread.done();
        flushThread.join();
        flushThread.checkNoError();

        putThread.join();
        putThread.checkNoError();
      } catch (InterruptedException ie) {
        LOG.warn("Caught exception when joining with flushThread", ie);
      }

      try {
          HBaseTestingUtility.closeRegionAndWAL(this.region);
      } catch (DroppedSnapshotException dse) {
        // We could get this on way out because we interrupt the background flusher and it could
        // fail anywhere causing a DSE over in the background flusher... only it is not properly
        // dealt with so could still be memory hanging out when we get to here -- memory we can't
        // flush because the accounting is 'off' since original DSE.
      }
      this.region = null;
    }
  }

};