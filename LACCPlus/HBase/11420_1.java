//,temp,TestSnapshotFromClient.java,446,476,temp,TestSnapshotFromClient.java,372,409
//,3
public class xxx {
  @Test
  public void testDeleteTableSnapshotsWithRegex() throws Exception {
    Admin admin = null;
    Pattern tableNamePattern = Pattern.compile("test.*");
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

      admin.deleteTableSnapshots(tableNamePattern, Pattern.compile("Table1.*"));
      assertEquals(1, admin.listTableSnapshots(tableNamePattern, MATCH_ALL).size());
    } finally {
      if (admin != null) {
        try {
          admin.deleteTableSnapshots(tableNamePattern, MATCH_ALL);
        } catch (SnapshotDoesNotExistException ignore) {
        }
        admin.close();
      }
    }
  }

};