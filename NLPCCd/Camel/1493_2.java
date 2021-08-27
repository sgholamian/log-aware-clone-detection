//,temp,sample_3877.java,2,17,temp,sample_4222.java,2,17
//,3
public class xxx {
public void dummy_method(){
Object result;
Object in = exchange.getIn().getBody();
if (in != null) {
Iterator<?> iter = ObjectHelper.createIterator(in);
while (iter.hasNext()) {
Object value = iter.next();
result = client.insert(statement, value);
doProcessResult(exchange, result);
}
} else {


log.info("inserting using statement");
}
}

};