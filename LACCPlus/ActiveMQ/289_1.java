//,temp,AMQ6459Test.java,163,203,temp,JDBCConcurrentDLQTest.java,182,209
//,3
public class xxx {
    private Long sendMessages(int messageCount) throws Exception {

        long numberOfMessageSent = 0;

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin", TRANSPORT_URL);


        Connection connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.start();


        final String blob = new String(new byte[4 * 1024]);
        try {

            Session producerSession = connection.createSession(true, Session.SESSION_TRANSACTED);
            MessageProducer jmsProducer = producerSession.createProducer(producerSession.createTopic(DESTINATION));

            Message sendMessage = producerSession.createTextMessage(blob);

            for (int i = 0; i < messageCount; i++) {

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