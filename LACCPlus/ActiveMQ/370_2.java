//,temp,JmsTransactedMessageOrderTest.java,112,136,temp,PriorityRedeliveryOrderTest.java,103,140
//,3
public class xxx {
    private Long sendMessages(int messageCount) throws Exception {

        long numberOfMessageSent = 0;

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(broker.getTransportConnectorByScheme("tcp").getPublishableConnectString());

        Connection connection = connectionFactory.createConnection();
        connection.start();

        try {

            Session producerSession = connection.createSession(true, Session.SESSION_TRANSACTED);
            MessageProducer jmsProducer = producerSession.createProducer(producerSession.createQueue(DESTINATION));

            Message sendMessage = producerSession.createTextMessage("test_message");

            for (int i = 0; i < messageCount; i++) {

                sendMessage.setIntProperty("appID", i);
                jmsProducer.send(sendMessage);
                producerSession.commit();
                numberOfMessageSent++;

            }

            LOG.info(" Finished after producing : " + numberOfMessageSent);
            return numberOfMessageSent;

        } catch (Exception ex) {
            LOG.info("Exception received producing ", ex);
            LOG.info("finishing after exception :" + numberOfMessageSent);
            return numberOfMessageSent;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

};