//,temp,TestDependentColumnFilter.java,152,175,temp,TestQualifierFilterWithEmptyQualifier.java,142,164
//,3
public class xxx {
  private void verifyScan(Scan s, long expectedRows, long expectedCells)
  throws IOException {
    InternalScanner scanner = this.region.getScanner(s);
    List<Cell> results = new ArrayList<>();
    int i = 0;
    int cells = 0;
    for (boolean done = true; done; i++) {
      done = scanner.next(results);
      Arrays.sort(results.toArray(new Cell[results.size()]),
          CellComparatorImpl.COMPARATOR);
      LOG.info("counter=" + i + ", " + results);
      if (results.isEmpty()) break;
      cells += results.size();
      assertTrue("Scanned too many rows! Only expected " + expectedRows +
          " total but already scanned " + (i+1), expectedRows > i);
      assertTrue("Expected " + expectedCells + " cells total but " +
          "already scanned " + cells, expectedCells >= cells);
      results.clear();
    }
    assertEquals("Expected " + expectedRows + " rows but scanned " + i +
        " rows", expectedRows, i);
    assertEquals("Expected " + expectedCells + " cells but scanned " + cells +
            " cells", expectedCells, cells);
  }

};