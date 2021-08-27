//,temp,sample_4220.java,2,13,temp,sample_2074.java,2,13
//,3
public class xxx {
private void doQueryForList(Exchange exchange) throws Exception {
SqlMapClient client = endpoint.getSqlMapClient();
Object result;
Object in = exchange.getIn().getBody();
if (in != null) {
result = client.queryForList(statement, in);
} else {


log.info("queryforlist using statement");
}
}

};