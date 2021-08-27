//,temp,MyBatisProducer.java,192,209,temp,MyBatisProducer.java,124,141
//,2
public class xxx {
    private void doDelete(Exchange exchange, SqlSession session) throws Exception {
        Object result;
        Object in = getInput(exchange);
        if (in != null) {
            // lets handle arrays or collections of objects
            Iterator<?> iter = ObjectHelper.createIterator(in);
            while (iter.hasNext()) {
                Object value = iter.next();
                LOG.trace("Deleting: {} using statement: {}", value, statement);
                result = session.delete(statement, value);
                doProcessResult(exchange, result, session);
            }
        } else {
            LOG.trace("Deleting using statement: {}", statement);
            result = session.delete(statement);
            doProcessResult(exchange, result, session);
        }
    }

};