//,temp,SchemaToolTaskMoveTable.java,130,141,temp,SchemaToolTaskMoveTable.java,88,103
//,3
public class xxx {
  private void updateTableId(Statement stmt) throws SQLException, HiveMetaException {
    // Find the old database id
    long oldDbId = getDbId(stmt, fromDb, fromCat);

    // Find the new database id
    long newDbId = getDbId(stmt, toDb, toCat);

    String update = String.format(schemaTool.quote(UPDATE_TABLE_ID_STMT), newDbId, oldDbId, tableName);
    LOG.debug("Going to run " + update);
    int numUpdated = stmt.executeUpdate(update);
    if (numUpdated != 1) {
      throw new HiveMetaException(
          "Failed to properly update TBLS table.  Expected to update " +
              "1 row but instead updated " + numUpdated);
    }
  }

};