//,temp,DbNotificationListener.java,1028,1069,temp,DbNotificationListener.java,991,1018
//,3
public class xxx {
  private long getNextEventId(Connection con, SQLGenerator sqlGenerator)
      throws SQLException, MetaException {

    /*
     * FOR UPDATE means something different in Derby than in most other vendor
     * implementations therefore it is required to lock the entire table. Since
     * there is only one row in the table, this should not cause any performance
     * degradation.
     *
     * see: https://db.apache.org/derby/docs/10.1/ref/rrefsqlj31783.html
     */
    if (sqlGenerator.getDbProduct().isDERBY()) {
      final String lockingQuery = sqlGenerator.lockTable("NOTIFICATION_SEQUENCE", false);
      LOG.debug("Locking Derby table [{}]", lockingQuery);
      try (Statement stmt = con.createStatement()) {
        stmt.execute(lockingQuery);
      }
    }

    final String sfuSql = sqlGenerator.addForUpdateClause(EV_SEL_SQL);
    Optional<Long> nextSequenceValue = Optional.empty();

    LOG.debug("Going to execute query [{}]", sfuSql);
    try (Statement stmt = con.createStatement()) {
      ResultSet rs = stmt.executeQuery(sfuSql);
      if (rs.next()) {
        nextSequenceValue = Optional.of(rs.getLong(1));
      }
    }

    final long updatedEventId = 1L + nextSequenceValue.orElseThrow(
        () -> new MetaException("Transaction database not properly configured, failed to determine next event ID"));

    LOG.debug("Going to execute query [{}][1={}]", EV_UPD_SQL, updatedEventId);
    try (PreparedStatement stmt = con.prepareStatement(EV_UPD_SQL)) {
      stmt.setLong(1, updatedEventId);
      final int rowCount = stmt.executeUpdate();
      LOG.debug("Updated {} rows for NOTIFICATION_SEQUENCE table", rowCount);
    }

    return nextSequenceValue.get();
  }

};