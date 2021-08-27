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

            int msgCount;
            int msgCommittedCount;

            public void onMessage(Message m) {
                try {
                    msgCount++;
                    TextMessage tm = (TextMessage)m;
                    receivedText = tm.getText();

                    if (tm.getJMSRedelivered()) {
                        msgRedelivered.add(receivedText);
                    }

                    LOG.info("consumer received message: " + receivedText + (tm.getJMSRedelivered() ? " ** Redelivered **" : ""));
                    if (msgCount == 3) {
                        msgRolledBack.add(receivedText);
                        consumerSession.rollback();
                        LOG.info("[msg: " + receivedText + "] ** rolled back **");
                    } else {
                        msgCommittedCount++;
                        msgCommitted.add(receivedText);
                        consumerSession.commit();
                        LOG.info("[msg: " + receivedText + "] committed transaction ");
                    }
                    if (msgCommittedCount == numMessages) {
                        latch.countDown();
                    }
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
            for (int i = 1; i <= numMessages; i++) {
                tm = producerSession.createTextMessage();
                tm.setText("Hello " + i);
                msgSent.add(tm.getText());
                producer.send(tm);
                LOG.info("producer sent message: " + tm.getText());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }

        LOG.info("Waiting for latch");
        latch.await();

        assertEquals(1, msgRolledBack.size());
        assertEquals(1, msgRedelivered.size());

        LOG.info("msg RolledBack = " + msgRolledBack.get(0));
        LOG.info("msg Redelivered = " + msgRedelivered.get(0));

        assertEquals(msgRolledBack.get(0), msgRedelivered.get(0));

        assertEquals(numMessages, msgSent.size());
        assertEquals(numMessages, msgCommitted.size());

        assertEquals(msgSent, msgCommitted);

    }

};