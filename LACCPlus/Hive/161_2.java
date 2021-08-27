//,temp,TxnHandler.java,873,889,temp,SchemaToolTaskMoveTable.java,111,120
//,3
public class xxx {
  private long getDbId(Statement stmt, String db, String catalog) throws SQLException, HiveMetaException {
    String query = String.format(schemaTool.quote(DB_ID_QUERY), db, catalog);
    LOG.debug("Going to run " + query);
    try (ResultSet rs = stmt.executeQuery(query)) {
      if (!rs.next()) {
        throw new HiveMetaException("Unable to find database " + fromDb);
      }
      return rs.getLong(1);
    }
  }

};