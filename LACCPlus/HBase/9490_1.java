//,temp,TestSpaceQuotasWithSnapshots.java,349,415,temp,TestSpaceQuotasWithSnapshots.java,193,290
//,3
public class xxx {
  @Test
  public void testRematerializedTablesDoNoInheritSpace() throws Exception {
    TableName tn = helper.createTableWithRegions(1);
    TableName tn2 = helper.getNextTableName();
    LOG.info("Writing data");
    // Set a quota on both tables
    QuotaSettings settings = QuotaSettingsFactory.limitTableSpace(
        tn, SpaceQuotaHelperForTests.ONE_GIGABYTE, SpaceViolationPolicy.NO_INSERTS);
    admin.setQuota(settings);
    QuotaSettings settings2 = QuotaSettingsFactory.limitTableSpace(
        tn2, SpaceQuotaHelperForTests.ONE_GIGABYTE, SpaceViolationPolicy.NO_INSERTS);
    admin.setQuota(settings2);
    // Write some data
    final long initialSize = 2L * SpaceQuotaHelperForTests.ONE_MEGABYTE;
    helper.writeData(tn, initialSize);

    LOG.info("Waiting until table size reflects written data");
    // Wait until that data is seen by the master
    TEST_UTIL.waitFor(30 * 1000, 500, new SpaceQuotaSnapshotPredicate(conn, tn) {
      @Override boolean evaluate(SpaceQuotaSnapshot snapshot) throws Exception {
        return snapshot.getUsage() >= initialSize;
      }
    });

    // Make sure we see the final quota usage size
    waitForStableQuotaSize(conn, tn, null);

    // The actual size on disk after we wrote our data the first time
    final long actualInitialSize = conn.getAdmin().getCurrentSpaceQuotaSnapshot(tn).getUsage();
    LOG.info("Initial table size was " + actualInitialSize);

    LOG.info("Snapshot the table");
    final String snapshot1 = tn.toString() + "_snapshot1";
    admin.snapshot(snapshot1, tn);

    admin.cloneSnapshot(snapshot1, tn2);

    // Write some more data to the first table
    helper.writeData(tn, initialSize, "q2");
    admin.flush(tn);

    // Watch the usage of the first table with some more data to know when the new
    // region size reports were sent to the master
    TEST_UTIL.waitFor(30_000, 1_000, new SpaceQuotaSnapshotPredicate(conn, tn) {
      @Override
      boolean evaluate(SpaceQuotaSnapshot snapshot) throws Exception {
        return snapshot.getUsage() >= actualInitialSize * 2;
      }
    });

    // We know that reports were sent by our RS, verify that they take up zero size.
    SpaceQuotaSnapshot snapshot =
      (SpaceQuotaSnapshot) conn.getAdmin().getCurrentSpaceQuotaSnapshot(tn2);
    assertNotNull(snapshot);
    assertEquals(0, snapshot.getUsage());

    // Compact the cloned table to force it to own its own files.
    TEST_UTIL.compact(tn2, true);
    // After the table is compacted, it should have its own files and be the same size as originally
    // But The compaction result file has an additional compaction event tracker
    TEST_UTIL.waitFor(30_000, 1_000, new SpaceQuotaSnapshotPredicate(conn, tn2) {
      @Override
      boolean evaluate(SpaceQuotaSnapshot snapshot) throws Exception {
        return snapshot.getUsage() >= actualInitialSize;
      }
    });
  }

};