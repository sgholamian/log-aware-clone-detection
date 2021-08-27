//,temp,sample_4221.java,2,15,temp,sample_4225.java,2,15
//,2
public class xxx {
private void doInsert(Exchange exchange) throws Exception {
SqlMapClient client = endpoint.getSqlMapClient();
Object result;
Object in = exchange.getIn().getBody();
if (in != null) {
Iterator<?> iter = ObjectHelper.createIterator(in);
while (iter.hasNext()) {
Object value = iter.next();


log.info("inserting using statement");
}
}
}

};