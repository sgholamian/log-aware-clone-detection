//,temp,TestSpaceQuotasWithSnapshots.java,349,415,temp,TestSpaceQuotasWithSnapshots.java,193,290
//,3
public class xxx {
  @Test
  public void testNamespacesInheritSnapshotSize() throws Exception {
    String ns = helper.createNamespace().getName();
    TableName tn = helper.createTableWithRegions(ns, 1);
    LOG.info("Writing data");
    // Set a quota
    QuotaSettings settings = QuotaSettingsFactory.limitNamespaceSpace(
        ns, SpaceQuotaHelperForTests.ONE_GIGABYTE, SpaceViolationPolicy.NO_INSERTS);
    admin.setQuota(settings);

    // Write some data and flush it to disk
    final long initialSize = 2L * SpaceQuotaHelperForTests.ONE_MEGABYTE;
    helper.writeData(tn, initialSize);
    admin.flush(tn);

    LOG.info("Waiting until namespace size reflects written data");
    // Wait until that data is seen by the master
    TEST_UTIL.waitFor(30 * 1000, 500, new SpaceQuotaSnapshotPredicate(conn, ns) {
      @Override boolean evaluate(SpaceQuotaSnapshot snapshot) throws Exception {
        return snapshot.getUsage() >= initialSize;
      }
    });

    // Make sure we see the "final" new size for the table, not some intermediate
    waitForStableQuotaSize(conn, null, ns);

    // The actual size on disk after we wrote our data the first time
    final long actualInitialSize = conn.getAdmin().getCurrentSpaceQuotaSnapshot(ns).getUsage();
    LOG.info("Initial table size was " + actualInitialSize);

    LOG.info("Snapshot the table");
    final String snapshot1 = tn.getQualifierAsString() + "_snapshot1";
    admin.snapshot(snapshot1, tn);

    // Write the same data again, then flush+compact. This should make sure that
    // the snapshot is referencing files that the table no longer references.
    LOG.info("Write more data");
    helper.writeData(tn, initialSize);
    LOG.info("Flush the table");
    admin.flush(tn);
    LOG.info("Synchronously compacting the table");
    TEST_UTIL.compact(tn, true);

    final long upperBound = initialSize + FUDGE_FOR_TABLE_SIZE;
    final long lowerBound = initialSize - FUDGE_FOR_TABLE_SIZE;

    LOG.info("Waiting for the region reports to reflect the correct size, between ("
        + lowerBound + ", " + upperBound + ")");
    TEST_UTIL.waitFor(30 * 1000, 500, new Predicate<Exception>() {
      @Override
      public boolean evaluate() throws Exception {
        Map<TableName, Long> sizes = conn.getAdmin().getSpaceQuotaTableSizes();
        LOG.debug("Master observed table sizes from region size reports: " + sizes);
        Long size = sizes.get(tn);
        if (null == size) {
          return false;
        }
        return size < upperBound && size > lowerBound;
      }
    });

    // Make sure we see the "final" new size for the table, not some intermediate
    waitForStableRegionSizeReport(conn, tn);
    final long finalSize = getRegionSizeReportForTable(conn, tn);
    assertNotNull("Did not expect to see a null size", finalSize);
    LOG.info("Final observed size of table: " + finalSize);

    // Make sure the QuotaObserverChore has time to reflect the new region size reports
    // (we saw above). The usage of the table should *not* decrease when we check it below,
    // though, because the snapshot on our table will cause the table to "retain" the size.
    TEST_UTIL.waitFor(20 * 1000, 500, new SpaceQuotaSnapshotPredicate(conn, ns) {
      @Override
      public boolean evaluate(SpaceQuotaSnapshot snapshot) throws Exception {
        return snapshot.getUsage() >= finalSize;
      }
    });

    // The final usage should be the sum of the initial size (referenced by the snapshot) and the
    // new size we just wrote above.
    long expectedFinalSize = actualInitialSize + finalSize;
    LOG.info(
        "Expecting namespace usage to be " + actualInitialSize + " + " + finalSize
        + " = " + expectedFinalSize);
    // The size of the table (WRT quotas) should now be approximately double what it was previously
    TEST_UTIL.waitFor(30 * 1000, 1000, new SpaceQuotaSnapshotPredicate(conn, ns) {
      @Override boolean evaluate(SpaceQuotaSnapshot snapshot) throws Exception {
        LOG.debug("Checking for " + expectedFinalSize + " == " + snapshot.getUsage());
        return expectedFinalSize == snapshot.getUsage();
      }
    });

    Map<String,Long> snapshotSizes = QuotaTableUtil.getObservedSnapshotSizes(conn);
    Long size = snapshotSizes.get(snapshot1);
    assertNotNull("Did not observe the size of the snapshot", size);
    assertEquals(
        "The recorded size of the HBase snapshot was not the size we expected", actualInitialSize,
        size.longValue());
  }

};