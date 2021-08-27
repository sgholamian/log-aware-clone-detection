//,temp,DefaultSqlProcessingStrategy.java,73,96,temp,DefaultSqlProcessingStrategy.java,41,71
//,3
public class xxx {
    @Override
    public int commit(
            final DefaultSqlEndpoint endpoint, final Exchange exchange, final Object data, final JdbcTemplate jdbcTemplate,
            final String query)
            throws Exception {

        final String preparedQuery
                = sqlPrepareStatementStrategy.prepareQuery(query, endpoint.isAllowNamedParameters(), exchange);

        return jdbcTemplate.execute(preparedQuery, new PreparedStatementCallback<Integer>() {
            public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException {
                int expected = ps.getParameterMetaData().getParameterCount();

                Iterator<?> iterator
                        = sqlPrepareStatementStrategy.createPopulateIterator(query, preparedQuery, expected, exchange, data);
                if (iterator != null) {
                    sqlPrepareStatementStrategy.populateStatement(ps, iterator, expected);
                    LOG.trace("Execute query {}", query);
                    ps.execute();

                    int updateCount = ps.getUpdateCount();
                    if (LOG.isTraceEnabled()) {
                        LOG.trace("Update count {}", updateCount);
                    }
                    return updateCount;
                }

                return 0;
            }
        });
    }

};