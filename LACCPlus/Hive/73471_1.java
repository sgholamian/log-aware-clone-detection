//,temp,CompactionTxnHandler.java,292,348,temp,CompactionTxnHandler.java,85,166
//,3
public class xxx {
  @Override
  @RetrySemantics.ReadOnly
  public List<CompactionInfo> findReadyToClean(long minOpenTxnWaterMark, long retentionTime) throws MetaException {
    Connection dbConn = null;
    List<CompactionInfo> rc = new ArrayList<>();

    Statement stmt = null;
    ResultSet rs = null;
    try {
      try {
        dbConn = getDbConn(Connection.TRANSACTION_READ_COMMITTED);
        stmt = dbConn.createStatement();
        /*
         * By filtering on minOpenTxnWaterMark, we will only cleanup after every transaction is committed, that could see
         * the uncompacted deltas. This way the cleaner can clean up everything that was made obsolete by this compaction.
         */
        String s = "SELECT \"CQ_ID\", \"CQ_DATABASE\", \"CQ_TABLE\", \"CQ_PARTITION\", "
                + "\"CQ_TYPE\", \"CQ_RUN_AS\", \"CQ_HIGHEST_WRITE_ID\" FROM \"COMPACTION_QUEUE\" WHERE \"CQ_STATE\" = '"
                + READY_FOR_CLEANING + "'";
        if (minOpenTxnWaterMark > 0) {
          s = s + " AND (\"CQ_NEXT_TXN_ID\" <= " + minOpenTxnWaterMark + " OR \"CQ_NEXT_TXN_ID\" IS NULL)";
        }
        if (retentionTime > 0) {
          s = s + " AND \"CQ_COMMIT_TIME\" < (" + getEpochFn(dbProduct) + " - " + retentionTime + ")";
        }
        LOG.debug("Going to execute query <" + s + ">");
        rs = stmt.executeQuery(s);

        while (rs.next()) {
          CompactionInfo info = new CompactionInfo();
          info.id = rs.getLong(1);
          info.dbname = rs.getString(2);
          info.tableName = rs.getString(3);
          info.partName = rs.getString(4);
          info.type = dbCompactionType2ThriftType(rs.getString(5).charAt(0));
          info.runAs = rs.getString(6);
          info.highestWriteId = rs.getLong(7);
          if (LOG.isDebugEnabled()) {
            LOG.debug("Found ready to clean: " + info.toString());
          }
          rc.add(info);
        }
        return rc;
      } catch (SQLException e) {
        LOG.error("Unable to select next element for cleaning, " + e.getMessage());
        LOG.debug("Going to rollback");
        rollbackDBConn(dbConn);
        checkRetryable(dbConn, e, "findReadyToClean");
        throw new MetaException("Unable to connect to transaction database " +
          StringUtils.stringifyException(e));
      } finally {
        close(rs, stmt, dbConn);
      }
    } catch (RetryException e) {
      return findReadyToClean(minOpenTxnWaterMark, retentionTime);
    }
  }

};