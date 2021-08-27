//,temp,sample_3873.java,2,17,temp,sample_4226.java,2,17
//,3
public class xxx {
public void dummy_method(){
Object result;
Object in = exchange.getIn().getBody();
if (in != null) {
Iterator<?> iter = ObjectHelper.createIterator(in);
while (iter.hasNext()) {
Object value = iter.next();
result = client.delete(statement, value);
doProcessResult(exchange, result);
}
} else {


log.info("deleting using statement");
}
}

};