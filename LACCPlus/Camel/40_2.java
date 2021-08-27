//,temp,ElsqlSqlProcessingStrategy.java,80,89,temp,ElsqlSqlProcessingStrategy.java,56,65
//,2
public class xxx {
            @Override
            public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.execute();

                int updateCount = ps.getUpdateCount();
                if (LOG.isTraceEnabled()) {
                    LOG.trace("Update count {}", updateCount);
                }
                return updateCount;
            }

};