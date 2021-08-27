//,temp,sample_1178.java,2,15,temp,sample_3872.java,2,14
//,3
public class xxx {
private void doUpdate(Exchange exchange, SqlSession session) throws Exception {
Object result;
Object in = getInput(exchange);
if (in != null) {
Iterator<?> iter = ObjectHelper.createIterator(in);
while (iter.hasNext()) {
Object value = iter.next();


log.info("updating using statement");
}
}
}

};