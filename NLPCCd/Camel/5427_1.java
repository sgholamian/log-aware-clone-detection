//,temp,sample_7617.java,2,8,temp,sample_7615.java,2,8
//,3
public class xxx {
public int commitBatchComplete(DefaultSqlEndpoint endpoint, NamedParameterJdbcTemplate namedJdbcTemplate, SqlParameterSource parameterSource, String query) throws Exception {
final SqlParameterSource param = new EmptySqlParameterSource();
final String sql = elSql.getSql(query, new SpringSqlParams(param));


log.info("commitbatchcomplete using sql");
}

};