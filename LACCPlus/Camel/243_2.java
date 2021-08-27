//,temp,MyBatisProducer.java,177,190,temp,MyBatisProducer.java,143,156
//,2
public class xxx {
    private void doInsertList(Exchange exchange, SqlSession session) throws Exception {
        Object result;
        Object in = getInput(exchange);
        if (in != null) {
            // just pass in the body as Object and allow MyBatis to iterate using its own foreach statement
            LOG.trace("Inserting: {} using statement: {}", in, statement);
            result = session.insert(statement, in);
            doProcessResult(exchange, result, session);
        } else {
            LOG.trace("Inserting using statement: {}", statement);
            result = session.insert(statement);
            doProcessResult(exchange, result, session);
        }
    }

};