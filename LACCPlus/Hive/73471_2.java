//,temp,CompactionTxnHandler.java,292,348,temp,CompactionTxnHandler.java,85,166
//,3
public class xxx {
  @Override
  @RetrySemantics.ReadOnly
  public Set<CompactionInfo> findPotentialCompactions(int abortedThreshold,
      long abortedTimeThreshold, long checkInterval) throws MetaException {
    Connection dbConn = null;
    Set<CompactionInfo> response = new HashSet<>();
    Statement stmt = null;
    ResultSet rs = null;
    try {
      try {
        dbConn = getDbConn(Connection.TRANSACTION_READ_COMMITTED);
        stmt = dbConn.createStatement();
        // Check for completed transactions
        final String s = "SELECT DISTINCT \"TC\".\"CTC_DATABASE\", \"TC\".\"CTC_TABLE\", \"TC\".\"CTC_PARTITION\" " +
          "FROM \"COMPLETED_TXN_COMPONENTS\" \"TC\" " + (checkInterval > 0 ?
          "LEFT JOIN ( " +
          "  SELECT \"C1\".* FROM \"COMPLETED_COMPACTIONS\" \"C1\" " +
          "  INNER JOIN ( " +
          "    SELECT MAX(\"CC_ID\") \"CC_ID\" FROM \"COMPLETED_COMPACTIONS\" " +
          "    GROUP BY \"CC_DATABASE\", \"CC_TABLE\", \"CC_PARTITION\"" +
          "  ) \"C2\" " +
          "  ON \"C1\".\"CC_ID\" = \"C2\".\"CC_ID\" " +
          "  WHERE \"C1\".\"CC_STATE\" IN (" + quoteChar(DID_NOT_INITIATE) + "," + quoteChar(FAILED_STATE) + ")" +
          ") \"C\" " +
          "ON \"TC\".\"CTC_DATABASE\" = \"C\".\"CC_DATABASE\" AND \"TC\".\"CTC_TABLE\" = \"C\".\"CC_TABLE\" " +
          "  AND (\"TC\".\"CTC_PARTITION\" = \"C\".\"CC_PARTITION\" OR (\"TC\".\"CTC_PARTITION\" IS NULL AND \"C\".\"CC_PARTITION\" IS NULL)) " +
          "WHERE \"C\".\"CC_ID\" IS NOT NULL OR " + isWithinCheckInterval("\"TC\".\"CTC_TIMESTAMP\"", checkInterval) : "");

        LOG.debug("Going to execute query <" + s + ">");
        rs = stmt.executeQuery(s);
        while (rs.next()) {
          CompactionInfo info = new CompactionInfo();
          info.dbname = rs.getString(1);
          info.tableName = rs.getString(2);
          info.partName = rs.getString(3);
          response.add(info);
        }
        rs.close();

        // Check for aborted txns: number of aborted txns past threshold and age of aborted txns
        // past time threshold
        boolean checkAbortedTimeThreshold = abortedTimeThreshold >= 0;
        String sCheckAborted = "SELECT \"TC_DATABASE\", \"TC_TABLE\", \"TC_PARTITION\", " +
          "MIN(\"TXN_STARTED\"), COUNT(*) FROM \"TXNS\", \"TXN_COMPONENTS\" " +
          "   WHERE \"TXN_ID\" = \"TC_TXNID\" AND \"TXN_STATE\" = " + TxnStatus.ABORTED + " " +
          "GROUP BY \"TC_DATABASE\", \"TC_TABLE\", \"TC_PARTITION\" " +
              (checkAbortedTimeThreshold ? "" : " HAVING COUNT(*) > " + abortedThreshold);

        LOG.debug("Going to execute query <" + sCheckAborted + ">");
        rs = stmt.executeQuery(sCheckAborted);
        long systemTime = System.currentTimeMillis();
        while (rs.next()) {
          boolean pastTimeThreshold =
              checkAbortedTimeThreshold && rs.getLong(4) + abortedTimeThreshold < systemTime;
          int numAbortedTxns = rs.getInt(5);
          if (numAbortedTxns > abortedThreshold || pastTimeThreshold) {
            CompactionInfo info = new CompactionInfo();
            info.dbname = rs.getString(1);
            info.tableName = rs.getString(2);
            info.partName = rs.getString(3);
            info.tooManyAborts = numAbortedTxns > abortedThreshold;
            info.hasOldAbort = pastTimeThreshold;
            if (LOG.isDebugEnabled()) {
              LOG.debug("Found potential compaction: " + info.toString());
            }
            response.add(info);
          }
        }
      } catch (SQLException e) {
        LOG.error("Unable to connect to transaction database " + e.getMessage());
        checkRetryable(dbConn, e,
            "findPotentialCompactions(maxAborted:" + abortedThreshold
                + ", abortedTimeThreshold:" + abortedTimeThreshold + ")");
      } finally {
        close(rs, stmt, dbConn);
      }
      return response;
    }
    catch (RetryException e) {
      return findPotentialCompactions(abortedThreshold, abortedTimeThreshold, checkInterval);
    }
  }

};