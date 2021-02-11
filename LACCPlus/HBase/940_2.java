//,temp,TestDependentColumnFilter.java,152,175,temp,TestQualifierFilterWithEmptyQualifier.java,142,164
//,3
public class xxx {
  private void verifyScanNoEarlyOut(Scan s, long expectedRows,
      long expectedKeys)
      throws IOException {
    InternalScanner scanner = this.region.getScanner(s);
    List<Cell> results = new ArrayList<>();
    int i = 0;
    for (boolean done = true; done; i++) {
      done = scanner.next(results);
      Arrays.sort(results.toArray(new Cell[results.size()]),
          CellComparator.getInstance());
      LOG.info("counter=" + i + ", " + results);
      if(results.isEmpty()) {
        break;
      }
      assertTrue("Scanned too many rows! Only expected " + expectedRows +
          " total but already scanned " + (i+1), expectedRows > i);
      assertEquals("Expected " + expectedKeys + " keys per row but " +
          "returned " + results.size(), expectedKeys, results.size());
      results.clear();
    }
    assertEquals("Expected " + expectedRows + " rows but scanned " + i +
        " rows", expectedRows, i);
  }

};