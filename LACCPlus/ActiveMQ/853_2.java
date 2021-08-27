//,temp,NetworkBrokerDetachTest.java,122,143,temp,PfcTimeoutTest.java,215,252
//,3
public class xxx {
    private int consumeMessages(BrokerService broker, int messageCount) throws Exception {

        int numberOfMessageConsumed = 0;

        ActiveMQConnectionFactory connectionFactory = newConnectionFactory(broker);
        Connection connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.start();

        Session consumerSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        try {


            MessageConsumer jmsConsumer = consumerSession.createConsumer(consumerSession.createQueue(DESTINATION));


            for (int i = 0; i < messageCount; i++) {
                jmsConsumer.receive(1000);
                numberOfMessageConsumed++;
            }

            LOG.info(" Finished after consuming  : " + numberOfMessageConsumed);
            return numberOfMessageConsumed;

        } catch (Exception ex) {

            LOG.info("Exception received producing ", ex);
            LOG.info("finishing after exception :" + numberOfMessageConsumed);


            return numberOfMessageConsumed;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

    }

};