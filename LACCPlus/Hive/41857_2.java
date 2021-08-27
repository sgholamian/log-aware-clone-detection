//,temp,TxnHandler.java,4260,4279,temp,TxnHandler.java,2484,2504
//,3
public class xxx {
  private boolean executeBoolean(String queryText, List<String> params, String errorMessage) throws MetaException {
    Connection dbConn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    try {
      dbConn = getDbConn(Connection.TRANSACTION_READ_COMMITTED);
      if (LOG.isDebugEnabled()) {
        LOG.debug("Going to execute query <" + queryText + ">");
      }
      pst = sqlGenerator.prepareStmtWithParameters(dbConn, queryText, params);
      pst.setMaxRows(1);
      rs = pst.executeQuery();

      return rs.next();
    } catch (SQLException ex) {
      LOG.warn(errorMessage, ex);
      throw new MetaException(errorMessage + " " + StringUtils.stringifyException(ex));
    } finally {
      close(rs, pst, dbConn);
    }
  }

};