//,temp,sample_3864.java,2,10,temp,sample_3865.java,2,12
//,3
public class xxx {
private void doSelectOne(Exchange exchange, SqlSession session) throws Exception {
Object result;
Object in = getInput(exchange);
if (in != null) {
result = session.selectOne(statement, in);
} else {


log.info("selectone using statement");
}
}

};