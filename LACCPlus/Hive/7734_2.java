//,temp,TxnHandler.java,2210,2243,temp,TxnHandler.java,2174,2208
//,3
public class xxx {
  @Override
  public MaxAllocatedTableWriteIdResponse getMaxAllocatedTableWrited(MaxAllocatedTableWriteIdRequest rqst) throws MetaException {
    String dbName = rqst.getDbName();
    String tableName = rqst.getTableName();
    try {
      Connection dbConn = null;
      PreparedStatement pStmt = null;
      ResultSet rs = null;
      try {
        dbConn = getDbConn(Connection.TRANSACTION_READ_COMMITTED);
        pStmt = sqlGenerator.prepareStmtWithParameters(dbConn, SELECT_NWI_NEXT_FROM_NEXT_WRITE_ID,
            Arrays.asList(dbName, tableName));
        LOG.debug("Going to execute query <" + SELECT_NWI_NEXT_FROM_NEXT_WRITE_ID.replaceAll("\\?", "{}") + ">",
            quoteString(dbName), quoteString(tableName));
        rs = pStmt.executeQuery();
        // If there is no record, we never allocated anything
        long maxWriteId = 0l;
        if (rs.next()) {
          // The row contains the nextId not the previously allocated
          maxWriteId = rs.getLong(1) - 1;
        }
        return new MaxAllocatedTableWriteIdResponse(maxWriteId);
      } catch (SQLException e) {
        LOG.error(
            "Exception during reading the max allocated writeId for dbName={}, tableName={}. Will retry if possible.",
            dbName, tableName, e);
        checkRetryable(dbConn, e, "getMaxAllocatedTableWrited(" + rqst + ")");
        throw new MetaException("Unable to update transaction database " + StringUtils.stringifyException(e));
      } finally {
        close(rs, pStmt, dbConn);
      }
    } catch (RetryException e) {
      return getMaxAllocatedTableWrited(rqst);
    }
  }

};