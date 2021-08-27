//,temp,sample_1550.java,2,9,temp,sample_5921.java,2,8
//,3
public class xxx {
public void process(final Exchange exchange) throws Exception {
final Object data = exchange.getIn().getBody();
final SqlParameterSource param = new ElsqlSqlMapSource(exchange, data);
final String sql = elSql.getSql(elSqlName, new SpringSqlParams(param));


log.info("elsqlproducer using sql");
}

};