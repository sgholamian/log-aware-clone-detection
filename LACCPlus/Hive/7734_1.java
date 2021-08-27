//,temp,TxnHandler.java,2210,2243,temp,TxnHandler.java,2174,2208
//,3
public class xxx {
  @Override
  public void seedWriteId(SeedTableWriteIdsRequest rqst)
      throws MetaException {
    try {
      Connection dbConn = null;
      PreparedStatement pst = null;
      try {
        dbConn = getDbConn(Connection.TRANSACTION_READ_COMMITTED);

        //since this is on conversion from non-acid to acid, NEXT_WRITE_ID should not have an entry
        //for this table.  It also has a unique index in case 'should not' is violated

        // First allocation of write id should add the table to the next_write_id meta table
        // The initial value for write id should be 1 and hence we add 1 with number of write ids
        // allocated here
        String s = "INSERT INTO \"NEXT_WRITE_ID\" (\"NWI_DATABASE\", \"NWI_TABLE\", \"NWI_NEXT\") VALUES (?, ?, "
                + Long.toString(rqst.getSeedWriteId() + 1) + ")";
        pst = sqlGenerator.prepareStmtWithParameters(dbConn, s, Arrays.asList(rqst.getDbName(), rqst.getTableName()));
        LOG.debug("Going to execute insert <" + s.replaceAll("\\?", "{}") + ">",
                quoteString(rqst.getDbName()), quoteString(rqst.getTableName()));
        pst.execute();
        LOG.debug("Going to commit");
        dbConn.commit();
      } catch (SQLException e) {
        rollbackDBConn(dbConn);
        checkRetryable(dbConn, e, "seedWriteId(" + rqst + ")");
        throw new MetaException("Unable to update transaction database " + StringUtils.stringifyException(e));
      } finally {
        close(null, pst, dbConn);
      }
    } catch (RetryException e) {
      seedWriteId(rqst);
    }
  }

};