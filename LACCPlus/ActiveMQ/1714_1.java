//,temp,PriorityRedeliveryOrderTest.java,146,207,temp,MessageGroupCloseTest.java,92,118
//,3
public class xxx {
    private int consumeMessages(int numberOfMessage) throws Exception {

        LOG.info("Creating new consumer for:" + numberOfMessage);


        int numberConsumedMessage = 0;
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(broker.getTransportConnectorByScheme("tcp").getPublishableConnectString());
        ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();

        try {

            connection.start();
            Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
            MessageConsumer jmsConsumer = session.createConsumer(session.createQueue(DESTINATION));
            boolean consume = true;


            while (consume) {

                Message message = jmsConsumer.receive(4000);

                if (message == null) {
                    LOG.info("Break on:" + numberConsumedMessage);
                    break;
                }


                int newAppId = message.getIntProperty("appID");

                numberConsumedMessage++;

                LOG.debug("Message newAppID" + newAppId);

                //check it is next appID in sequence

                if (newAppId != (consumedAppId + 1)) {
                    fail(" newAppId is " + newAppId + " expected " + (consumedAppId + 1));
                }

                //increase next AppID
                consumedAppId = newAppId;

                session.commit();

                if (numberConsumedMessage == numberOfMessage) {
                    LOG.info("closing consumer after 200 message, consumedAppID is " + consumedAppId);
                    return numberConsumedMessage;
                }

            }
        } finally {

            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ex) {

                }
            }
        }
        return numberConsumedMessage;
    }

};