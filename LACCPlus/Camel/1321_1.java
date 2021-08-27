//,temp,MyBatisProducer.java,211,224,temp,MyBatisProducer.java,110,122
//,3
public class xxx {
    private void doDeleteList(Exchange exchange, SqlSession session) throws Exception {
        Object result;
        Object in = getInput(exchange);
        if (in != null) {
            // just pass in the body as Object and allow MyBatis to iterate using its own foreach statement
            LOG.trace("Deleting: {} using statement: {}", in, statement);
            result = session.delete(statement, in);
            doProcessResult(exchange, result, session);
        } else {
            LOG.trace("Deleting using statement: {}", statement);
            result = session.delete(statement);
            doProcessResult(exchange, result, session);
        }
    }

};