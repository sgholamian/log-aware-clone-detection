//,temp,sample_3868.java,2,14,temp,sample_1177.java,2,13
//,3
public class xxx {
private void doInsert(Exchange exchange, SqlSession session) throws Exception {
Object result;
Object in = getInput(exchange);
if (in != null) {
Iterator<?> iter = ObjectHelper.createIterator(in);
while (iter.hasNext()) {
Object value = iter.next();


log.info("inserting using statement");
}
}
}

};