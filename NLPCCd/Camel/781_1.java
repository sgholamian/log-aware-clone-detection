//,temp,sample_3867.java,2,12,temp,sample_3870.java,2,10
//,3
public class xxx {
private void doSelectList(Exchange exchange, SqlSession session) throws Exception {
Object result;
Object in = getInput(exchange);
if (in != null) {
result = session.selectList(statement, in);
} else {


log.info("selectlist using statement");
}
}

};