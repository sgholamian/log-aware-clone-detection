//,temp,TestSnapshotFromClient.java,446,476,temp,TestSnapshotFromClient.java,372,409
//,3
public class xxx {
  @Test
  public void testListTableSnapshotsWithRegex() throws Exception {
    Admin admin = null;
    try {
      admin = UTIL.getAdmin();

      String table1Snapshot1 = "Table1Snapshot1";
      admin.snapshot(table1Snapshot1, TABLE_NAME);
      LOG.debug("Snapshot1 completed.");

      String table1Snapshot2 = "Table1Snapshot2";
      admin.snapshot(table1Snapshot2, TABLE_NAME);
      LOG.debug("Snapshot2 completed.");

      String table2Snapshot1 = "Table2Snapshot1";
      admin.snapshot(Bytes.toBytes(table2Snapshot1), TABLE_NAME);
      LOG.debug(table2Snapshot1 + " completed.");

      List<SnapshotDescription> listTableSnapshots =
          admin.listTableSnapshots(Pattern.compile("test.*"), Pattern.compile("Table1.*"));
      List<String> listTableSnapshotNames = new ArrayList<>();
      assertEquals(2, listTableSnapshots.size());
      for (SnapshotDescription s : listTableSnapshots) {
        listTableSnapshotNames.add(s.getName());
      }
      assertTrue(listTableSnapshotNames.contains(table1Snapshot1));
      assertTrue(listTableSnapshotNames.contains(table1Snapshot2));
      assertFalse(listTableSnapshotNames.contains(table2Snapshot1));
    } finally {
      if (admin != null) {
        try {
          admin.deleteSnapshots(Pattern.compile("Table.*"));
        } catch (SnapshotDoesNotExistException ignore) {
        }
        admin.close();
      }
    }
  }

};