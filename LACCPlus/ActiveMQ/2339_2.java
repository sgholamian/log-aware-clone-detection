//,temp,SimpleNetworkTest.java,96,128,temp,ThreeBrokerTempDestDemandSubscriptionCleanupTest.java,208,239
//,3
public class xxx {
    private void installEchoClientOnBrokerC() throws Exception {
        BrokerItem brokerC = brokers.get(BROKER_C);
        Connection conn = brokerC.createConnection();
        conn.start();

        final Session sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = sess.createQueue(ECHO_QUEUE_NAME);
        MessageConsumer consumer = sess.createConsumer(destination);

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {

                TextMessage textMessage = (TextMessage) message;

                try {
                    Destination replyTo = message.getJMSReplyTo();

                    MessageProducer producer = sess.createProducer(replyTo);
                    Message response = sess.createTextMessage(textMessage.getText());

                    LOG.info("Replying to this request: "  + textMessage.getText());
                    producer.send(response);
                    producer.close();

                } catch (JMSException e) {
                    e.printStackTrace();
                    fail("Could not respond to an echo request");
                }
            }
        });
    }

};