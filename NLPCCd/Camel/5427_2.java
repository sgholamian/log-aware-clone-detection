//,temp,sample_7617.java,2,8,temp,sample_7615.java,2,8
//,3
public class xxx {
public int commit(DefaultSqlEndpoint defaultSqlEndpoint, Exchange exchange, Object data, NamedParameterJdbcTemplate jdbcTemplate, SqlParameterSource parameterSource, String query) throws Exception {
final SqlParameterSource param = new ElsqlSqlMapSource(exchange, data);
final String sql = elSql.getSql(query, new SpringSqlParams(param));


log.info("commit using sql");
}

};