//,temp,sample_3876.java,2,14,temp,sample_4223.java,2,15
//,3
public class xxx {
private void doDelete(Exchange exchange, SqlSession session) throws Exception {
Object result;
Object in = getInput(exchange);
if (in != null) {
Iterator<?> iter = ObjectHelper.createIterator(in);
while (iter.hasNext()) {
Object value = iter.next();


log.info("deleting using statement");
}
}
}

};