//,temp,DefaultSqlProcessingStrategy.java,73,96,temp,DefaultSqlProcessingStrategy.java,41,71
//,3
public class xxx {
    @Override
    public int commitBatchComplete(final DefaultSqlEndpoint endpoint, final JdbcTemplate jdbcTemplate, final String query)
            throws Exception {
        final String preparedQuery = sqlPrepareStatementStrategy.prepareQuery(query, endpoint.isAllowNamedParameters(), null);

        return jdbcTemplate.execute(preparedQuery, new PreparedStatementCallback<Integer>() {
            public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException {
                int expected = ps.getParameterMetaData().getParameterCount();
                if (expected != 0) {
                    throw new IllegalArgumentException(
                            "Query onConsumeBatchComplete " + query + " cannot have parameters, was " + expected);
                }

                LOG.trace("Execute query {}", query);
                ps.execute();

                int updateCount = ps.getUpdateCount();
                if (LOG.isTraceEnabled()) {
                    LOG.trace("Update count {}", updateCount);
                }
                return updateCount;
            }
        });
    }

};