//,temp,TrapMessageInJDBCStoreTest.java,155,191,temp,TempStoreDataCleanupTest.java,214,239
//,3
public class xxx {
    public List<TextMessage> consumeMessages(String queue,
                                      String transportURL) throws JMSException {
        Connection connection = null;
        LOG.debug("*** consumeMessages() called ...");

        try {

            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
                    transportURL);

            connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queue);

            ArrayList<TextMessage> consumedMessages = new ArrayList<TextMessage>();

            MessageConsumer messageConsumer = session.createConsumer(destination);

            while(true){
                TextMessage textMessage= (TextMessage) messageConsumer.receive(4000);
                LOG.debug("*** consumed Messages :"+textMessage);

                if(textMessage==null){
                    return consumedMessages;
                }
                consumedMessages.add(textMessage);
            }


        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

};