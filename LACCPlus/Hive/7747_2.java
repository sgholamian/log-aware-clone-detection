//,temp,DbNotificationListener.java,1028,1069,temp,DbNotificationListener.java,991,1018
//,3
public class xxx {
  private long getNextNLId(Connection con, SQLGenerator sqlGenerator, String sequence)
          throws SQLException, MetaException {

    final String sfuSql = sqlGenerator.addForUpdateClause(NL_SEL_SQL);
    Optional<Long> nextSequenceValue = Optional.empty();

    LOG.debug("Going to execute query [{}][1={}]", sfuSql, sequence);
    try (PreparedStatement stmt = con.prepareStatement(sfuSql)) {
      stmt.setString(1, sequence);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        nextSequenceValue = Optional.of(rs.getLong(1));
      }
    }

    final long updatedNLId = 1L + nextSequenceValue.orElseThrow(
        () -> new MetaException("Transaction database not properly configured, failed to determine next NL ID"));

    LOG.debug("Going to execute query [{}][1={}][2={}]", NL_UPD_SQL, updatedNLId, sequence);
    try (PreparedStatement stmt = con.prepareStatement(NL_UPD_SQL)) {
      stmt.setLong(1, updatedNLId);
      stmt.setString(2, sequence);
      final int rowCount = stmt.executeUpdate();
      LOG.debug("Updated {} rows for sequnce {}", rowCount, sequence);
    }

    return nextSequenceValue.get();
  }

};