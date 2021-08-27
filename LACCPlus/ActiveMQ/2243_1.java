//,temp,AMQ2084Test.java,110,142,temp,AMQ2084Test.java,74,108
//,3
public class xxx {
    public void listenTopic(final String topicName, final String selectors) {
        try {
            Properties props = new Properties();
            props.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            props.put("java.naming.provider.url", connectionUri);
            props.put("topic.topicName", topicName);

            javax.naming.Context ctx = new InitialContext(props);
            TopicConnectionFactory factory = (TopicConnectionFactory) ctx.lookup("ConnectionFactory");
            TopicConnection conn = factory.createTopicConnection();
            final Topic topic = (Topic) ctx.lookup("topicName");
            TopicSession session = conn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            TopicSubscriber receiver = session.createSubscriber(topic, selectors, false);

            receiver.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    try {
                        if (message instanceof TextMessage) {
                            TextMessage txtMsg = (TextMessage) message;
                            String msg = txtMsg.getText();
                            LOG.info("Topic Message Received: " + topicName + " - " + msg);
                        }
                        message.acknowledge();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            conn.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

};