//,temp,ElsqlSqlProcessingStrategy.java,69,91,temp,ElsqlSqlProcessingStrategy.java,45,67
//,3
public class xxx {
    @Override
    public int commit(
            DefaultSqlEndpoint defaultSqlEndpoint, Exchange exchange, Object data, NamedParameterJdbcTemplate jdbcTemplate,
            SqlParameterSource parameterSource, String query)
            throws Exception {

        final SqlParameterSource param = new ElsqlSqlMapSource(exchange, data);
        final String sql = elSql.getSql(query, new SpringSqlParams(param));
        LOG.debug("commit @{} using sql: {}", query, sql);

        return jdbcTemplate.execute(sql, param, new PreparedStatementCallback<Integer>() {
            @Override
            public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
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