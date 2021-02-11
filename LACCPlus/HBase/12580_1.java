//,temp,TestFilterListOrOperatorWithBlkCnt.java,100,141,temp,TestMultiRowRangeFilter.java,352,378
//,3
public class xxx {
  @Test
  public void testMultiRowRangeWithFilterListOrOperatorWithBlkCnt() throws IOException {
    tableName = TableName.valueOf(name.getMethodName());
    Table ht = TEST_UTIL.createTable(tableName, family, Integer.MAX_VALUE);
    generateRows(numRows, ht, family, qf, value);

    Scan scan = new Scan();
    scan.setMaxVersions();
    long blocksStart = getBlkAccessCount();

    List<RowRange> ranges1 = new ArrayList<>();
    ranges1.add(new RowRange(Bytes.toBytes(10), true, Bytes.toBytes(15), false));
    ranges1.add(new RowRange(Bytes.toBytes(9980), true, Bytes.toBytes(9985), false));

    MultiRowRangeFilter filter1 = new MultiRowRangeFilter(ranges1);

    List<RowRange> ranges2 = new ArrayList<>();
    ranges2.add(new RowRange(Bytes.toBytes(15), true, Bytes.toBytes(20), false));
    ranges2.add(new RowRange(Bytes.toBytes(9985), true, Bytes.toBytes(9990), false));

    MultiRowRangeFilter filter2 = new MultiRowRangeFilter(ranges2);

    FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
    filterList.addFilter(filter1);
    filterList.addFilter(filter2);
    scan.setFilter(filterList);
    int resultsSize = getResultsSize(ht, scan);
    LOG.info("found " + resultsSize + " results");
    List<Cell> results1 = getScanResult(Bytes.toBytes(10), Bytes.toBytes(20), ht);
    List<Cell> results2 = getScanResult(Bytes.toBytes(9980), Bytes.toBytes(9990), ht);

    assertEquals(results1.size() + results2.size(), resultsSize);
    long blocksEnd = getBlkAccessCount();
    long diff = blocksEnd - blocksStart;
    LOG.info("Diff in number of blocks " + diff);
    /*
     * Verify that we don't read all the blocks (8 in total).
     */
    assertEquals(4, diff);

    ht.close();
  }

};