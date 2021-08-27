//,temp,sample_4219.java,2,11,temp,sample_4218.java,2,13
//,3
public class xxx {
private void doQueryForObject(Exchange exchange) throws Exception {
SqlMapClient client = endpoint.getSqlMapClient();
Object result;
Object in = exchange.getIn().getBody();
if (in != null) {
result = client.queryForObject(statement, in);
} else {


log.info("queryforobject using statement");
}
}

};