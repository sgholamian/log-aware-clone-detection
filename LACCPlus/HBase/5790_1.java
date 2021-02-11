//,temp,TestRegionIncrement.java,211,239,temp,TestRegionIncrement.java,174,206
//,3
public class xxx {
  @Test
  public void testContendedAcrossCellsIncrement() throws IOException, InterruptedException {
    final HRegion region = getRegion(TEST_UTIL.getConfiguration(),
        TestIncrementsFromClientSide.filterStringSoTableNameSafe(this.name.getMethodName()));
    long startTime = System.currentTimeMillis();
    try {
      CrossRowCellIncrementer [] threads = new CrossRowCellIncrementer[THREAD_COUNT];
      for (int i = 0; i < threads.length; i++) {
        threads[i] = new CrossRowCellIncrementer(i, INCREMENT_COUNT, region, THREAD_COUNT);
      }
      for (int i = 0; i < threads.length; i++) {
        threads[i].start();
      }
      for (int i = 0; i < threads.length; i++) {
        threads[i].join();
      }
      RegionScanner regionScanner = region.getScanner(new Scan());
      List<Cell> cells = new ArrayList<>(100);
      while(regionScanner.next(cells)) continue;
      assertEquals(THREAD_COUNT, cells.size());
      long total = 0;
      for (Cell cell: cells) total +=
        Bytes.toLong(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
      assertEquals(INCREMENT_COUNT * THREAD_COUNT, total);
    } finally {
      closeRegion(region);
      LOG.info(this.name.getMethodName() + " " + (System.currentTimeMillis() - startTime) + "ms");
    }
  }

};