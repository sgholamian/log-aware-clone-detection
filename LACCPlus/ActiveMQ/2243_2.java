//,temp,AMQ2084Test.java,110,142,temp,AMQ2084Test.java,74,108
//,3
public class xxx {
    public void listenQueue(final String queueName, final String selectors) {
        try {
            Properties props = new Properties();
            props.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            props.put("java.naming.provider.url", connectionUri);
            props.put("queue.queueName", queueName);

            javax.naming.Context ctx = new InitialContext(props);
            QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup("ConnectionFactory");
            QueueConnection conn = factory.createQueueConnection();
            final Queue queue = (Queue) ctx.lookup("queueName");
            QueueSession session = conn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            QueueReceiver receiver = session.createReceiver(queue, selectors);
            System.out.println("Message Selector: " + receiver.getMessageSelector());
            receiver.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    try {
                        if (message instanceof TextMessage) {
                            TextMessage txtMsg = (TextMessage) message;
                            String msg = txtMsg.getText();
                            LOG.info("Queue Message Received: " + queueName + " - " + msg);
                            qreceived.countDown();

                        }
                        message.acknowledge();
                    } catch (Throwable e) {
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