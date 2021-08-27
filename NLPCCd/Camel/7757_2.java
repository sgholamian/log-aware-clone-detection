//,temp,sample_3876.java,2,14,temp,sample_4223.java,2,15
//,3
public class xxx {
private void doUpdate(Exchange exchange) throws Exception {
SqlMapClient client = endpoint.getSqlMapClient();
Object result;
Object in = exchange.getIn().getBody();
if (in != null) {
Iterator<?> iter = ObjectHelper.createIterator(in);
while (iter.hasNext()) {
Object value = iter.next();


log.info("updating using statement");
}
}
}

};