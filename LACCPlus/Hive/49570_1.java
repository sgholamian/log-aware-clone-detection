//,temp,TxnHandler.java,427,456,temp,CompactionTxnHandler.java,1230,1271
//,3
public class xxx {
  private boolean checkMinHistoryLevelTable(boolean configValue) throws MetaException {
    if (!configValue) {
      // don't check it if disabled
      return false;
    }
    Connection dbConn = null;
    boolean tableExists = true;
    try {
      dbConn = getDbConn(Connection.TRANSACTION_READ_COMMITTED);
      try (Statement stmt = dbConn.createStatement()) {
        // Dummy query to see if table exists
        try (ResultSet rs = stmt.executeQuery("SELECT 1 FROM \"MIN_HISTORY_LEVEL\"")) {
          rs.next();
        }
      }
      dbConn.rollback();
    } catch (SQLException e) {
      rollbackDBConn(dbConn);
      LOG.debug("Catching sql exception in min history level check", e);
      if (dbProduct.isTableNotExistsError(e)) {
        tableExists = false;
      } else {
        throw new MetaException(
            "Unable to select from transaction database: " + getMessage(e) + StringUtils.stringifyException(e));
      }
    } finally {
      closeDbConn(dbConn);
    }
    return tableExists;
  }

};