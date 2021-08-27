//,temp,sample_3879.java,2,13,temp,sample_3875.java,2,13
//,2
public class xxx {
private void doDeleteList(Exchange exchange, SqlSession session) throws Exception {
Object result;
Object in = getInput(exchange);
if (in != null) {
result = session.delete(statement, in);
doProcessResult(exchange, result, session);
} else {


log.info("deleting using statement");
}
}

};