//,temp,TestHRegion.java,3562,3650,temp,TestHRegion.java,3431,3495
//,3
public class xxx {
  @Test
  public void testFlushCacheWhileScanning() throws IOException, InterruptedException {
    byte[] family = Bytes.toBytes("family");
    int numRows = 1000;
    int flushAndScanInterval = 10;
    int compactInterval = 10 * flushAndScanInterval;

    this.region = initHRegion(tableName, method, CONF, family);
    FlushThread flushThread = new FlushThread();
    try {
      flushThread.start();

      Scan scan = new Scan();
      scan.addFamily(family);
      scan.setFilter(new SingleColumnValueFilter(family, qual1, CompareOp.EQUAL,
          new BinaryComparator(Bytes.toBytes(5L))));

      int expectedCount = 0;
      List<Cell> res = new ArrayList<>();

      boolean toggle = true;
      for (long i = 0; i < numRows; i++) {
        Put put = new Put(Bytes.toBytes(i));
        put.setDurability(Durability.SKIP_WAL);
        put.addColumn(family, qual1, Bytes.toBytes(i % 10));
        region.put(put);

        if (i != 0 && i % compactInterval == 0) {
          LOG.debug("iteration = " + i+ " ts="+System.currentTimeMillis());
          region.compact(true);
        }

        if (i % 10 == 5L) {
          expectedCount++;
        }

        if (i != 0 && i % flushAndScanInterval == 0) {
          res.clear();
          InternalScanner scanner = region.getScanner(scan);
          if (toggle) {
            flushThread.flush();
          }
          while (scanner.next(res))
            ;
          if (!toggle) {
            flushThread.flush();
          }
          assertEquals("toggle="+toggle+"i=" + i + " ts="+System.currentTimeMillis(),
              expectedCount, res.size());
          toggle = !toggle;
        }
      }

    } finally {
      try {
        flushThread.done();
        flushThread.join();
        flushThread.checkNoError();
      } catch (InterruptedException ie) {
        LOG.warn("Caught exception when joining with flushThread", ie);
      }
      HBaseTestingUtility.closeRegionAndWAL(this.region);
      this.region = null;
    }
  }

};