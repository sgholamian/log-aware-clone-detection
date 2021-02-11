//,temp,TestMultiRowRangeFilter.java,444,479,temp,TestMultiRowRangeFilter.java,409,442
//,3
public class xxx {
  @Test
  public void testMultiRowRangeWithFilterListOrOperator() throws IOException {
    tableName = TableName.valueOf(name.getMethodName());
    Table ht = TEST_UTIL.createTable(tableName, family, Integer.MAX_VALUE);
    generateRows(numRows, ht, family, qf, value);

    Scan scan = new Scan();
    scan.setMaxVersions();

    List<RowRange> ranges1 = new ArrayList<>();
    ranges1.add(new RowRange(Bytes.toBytes(30), true, Bytes.toBytes(40), false));
    ranges1.add(new RowRange(Bytes.toBytes(10), true, Bytes.toBytes(20), false));
    ranges1.add(new RowRange(Bytes.toBytes(60), true, Bytes.toBytes(70), false));

    MultiRowRangeFilter filter1 = new MultiRowRangeFilter(ranges1);

    List<RowRange> ranges2 = new ArrayList<>();
    ranges2.add(new RowRange(Bytes.toBytes(20), true, Bytes.toBytes(40), false));
    ranges2.add(new RowRange(Bytes.toBytes(80), true, Bytes.toBytes(90), false));

    MultiRowRangeFilter filter2 = new MultiRowRangeFilter(ranges2);

    FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
    filterList.addFilter(filter1);
    filterList.addFilter(filter2);
    scan.setFilter(filterList);
    int resultsSize = getResultsSize(ht, scan);
    LOG.info("found " + resultsSize + " results");
    List<Cell> results1 = getScanResult(Bytes.toBytes(10), Bytes.toBytes(40), ht);
    List<Cell> results2 = getScanResult(Bytes.toBytes(60), Bytes.toBytes(70), ht);
    List<Cell> results3 = getScanResult(Bytes.toBytes(80), Bytes.toBytes(90), ht);

    assertEquals(results1.size() + results2.size() + results3.size(),resultsSize);

    ht.close();
  }

};