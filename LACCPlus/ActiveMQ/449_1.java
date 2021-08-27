//,temp,TransactionTest.java,59,113,temp,TransactionRollbackOrderTest.java,64,150
//,3
public class xxx {
    public void testTransaction() throws Exception {

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
        connection = factory.createConnection();
        queue = new ActiveMQQueue(getClass().getName() + "." + getName());

        producerSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        consumerSession = connection.createSession(true, 0);

        producer = producerSession.createProducer(queue);

        consumer = consumerSession.createConsumer(queue);
        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message m) {
                try {
                    TextMessage tm = (TextMessage)m;
                    receivedText = tm.getText();
                    latch.countDown();

                    LOG.info("consumer received message :" + receivedText);
                    consumerSession.commit();
                    LOG.info("committed transaction");
                } catch (JMSException e) {
                    try {
                        consumerSession.rollback();
                        LOG.info("rolled back transaction");
                    } catch (JMSException e1) {
                        LOG.info(e1.toString());
                        e1.printStackTrace();
                    }
                    LOG.info(e.toString());
                    e.printStackTrace();
                }
            }
        });

        connection.start();

        TextMessage tm = null;
        try {
            tm = producerSession.createTextMessage();
            tm.setText("Hello, " + new Date());
            producer.send(tm);
            LOG.info("producer sent message :" + tm.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }

        LOG.info("Waiting for latch");
        latch.await(2,TimeUnit.SECONDS);
        assertNotNull(receivedText);
        LOG.info("test completed, destination=" + receivedText);
    }

};