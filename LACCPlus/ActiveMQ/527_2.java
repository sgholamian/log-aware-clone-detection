//,temp,DeadLetterTestSupport.java,116,121,temp,AMQ3405Test.java,158,163
//,2
public class xxx {
    protected void makeDlqConsumer() throws JMSException {
        dlqDestination = createDlqDestination();

        LOG.info("Consuming from dead letter on: " + dlqDestination);
        dlqConsumer = dlqSession.createConsumer(dlqDestination);
    }

};