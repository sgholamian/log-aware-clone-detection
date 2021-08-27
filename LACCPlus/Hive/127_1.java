//,temp,SchemaToolTaskMoveTable.java,130,141,temp,SchemaToolTaskMoveTable.java,88,103
//,3
public class xxx {
  private void updateDbNameForTable(Statement stmt, String tableName, String tableColumnName, String fromCat,
      String toCat, String fromDb, String toDb, String hiveTblName) throws HiveMetaException, SQLException {
    String update = String.format(schemaTool.quote(UPDATE_DB_NAME_STMT), tableName, toCat, toDb, fromCat, fromDb,
        tableColumnName, hiveTblName);

    LOG.debug("Going to run " + update);
    int numUpdated = stmt.executeUpdate(update);
    if (numUpdated > 1 || numUpdated < 0) {
      throw new HiveMetaException("Failed to properly update the " + tableName +
          " table.  Expected to update 1 row but instead updated " + numUpdated);
    }
  }

};