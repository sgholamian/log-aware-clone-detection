//,temp,TestMultiRowRangeFilter.java,282,307,temp,TestMultiRowRangeFilter.java,254,280
//,3
public class xxx {
  @Test
  public void testMultiRowRangeFilterWithRangeOverlap() throws IOException {
    tableName = TableName.valueOf(name.getMethodName());
    Table ht = TEST_UTIL.createTable(tableName, family, Integer.MAX_VALUE);
    generateRows(numRows, ht, family, qf, value);

    Scan scan = new Scan();
    scan.setMaxVersions();

    List<RowRange> ranges = new ArrayList<>();
    ranges.add(new RowRange(Bytes.toBytes(10), true, Bytes.toBytes(20), false));
    ranges.add(new RowRange(Bytes.toBytes(15), true, Bytes.toBytes(40), false));
    ranges.add(new RowRange(Bytes.toBytes(65), true, Bytes.toBytes(75), false));
    ranges.add(new RowRange(Bytes.toBytes(60), true, null, false));
    ranges.add(new RowRange(Bytes.toBytes(60), true, Bytes.toBytes(80), false));

    MultiRowRangeFilter filter = new MultiRowRangeFilter(ranges);
    scan.setFilter(filter);
    int resultsSize = getResultsSize(ht, scan);
    LOG.info("found " + resultsSize + " results");
    List<Cell> results1 = getScanResult(Bytes.toBytes(10), Bytes.toBytes(40), ht);
    List<Cell> results2 = getScanResult(Bytes.toBytes(60), Bytes.toBytes(""), ht);

    assertEquals(results1.size() + results2.size(), resultsSize);

    ht.close();
  }

};