//,temp,sample_3873.java,2,17,temp,sample_4226.java,2,17
//,3
public class xxx {
public void dummy_method(){
Object result;
Object in = getInput(exchange);
if (in != null) {
Iterator<?> iter = ObjectHelper.createIterator(in);
while (iter.hasNext()) {
Object value = iter.next();
result = session.update(statement, value);
doProcessResult(exchange, result, session);
}
} else {


log.info("updating using statement");
}
}

};