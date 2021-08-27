//,temp,MyBatisProducer.java,211,224,temp,MyBatisProducer.java,110,122
//,3
public class xxx {
    private void doSelectList(Exchange exchange, SqlSession session) throws Exception {
        Object result;
        Object in = getInput(exchange);
        if (in != null) {
            LOG.trace("SelectList: {} using statement: {}", in, statement);
            result = session.selectList(statement, in);
        } else {
            LOG.trace("SelectList using statement: {}", statement);
            result = session.selectList(statement);
        }

        doProcessResult(exchange, result, session);
    }

};