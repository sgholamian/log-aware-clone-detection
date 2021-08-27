//,temp,RedeliveryPolicyTest.java,642,709,temp,RedeliveryPolicyTest.java,467,547
//,3
public class xxx {
    public void testRepeatedRedeliveryOnMessageNoCommit() throws Exception {

        connection.start();
        Session dlqSession = connection.createSession(true, Session.SESSION_TRANSACTED);
        ActiveMQQueue destination = new ActiveMQQueue("TEST");
        MessageProducer producer = dlqSession.createProducer(destination);

        // Send the messages
        producer.send(dlqSession.createTextMessage("1st"));

        dlqSession.commit();
        MessageConsumer dlqConsumer = dlqSession.createConsumer(new ActiveMQQueue("ActiveMQ.DLQ"));

        final int maxRedeliveries = 4;
        final AtomicInteger receivedCount = new AtomicInteger(0);

        for (int i=0;i<=maxRedeliveries+1;i++) {

            connection = (ActiveMQConnection)factory.createConnection(userName, password);
            connections.add(connection);

            RedeliveryPolicy policy = connection.getRedeliveryPolicy();
            policy.setInitialRedeliveryDelay(0);
            policy.setUseExponentialBackOff(false);
            policy.setMaximumRedeliveries(maxRedeliveries);

            connection.start();
            final Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
            MessageConsumer consumer = session.createConsumer(destination);
            final CountDownLatch done = new CountDownLatch(1);

            consumer.setMessageListener(new MessageListener(){
                @Override
                public void onMessage(Message message) {
                    try {
                        ActiveMQTextMessage m = (ActiveMQTextMessage)message;
                        assertEquals("1st", m.getText());
                        assertEquals(receivedCount.get(), m.getRedeliveryCounter());
                        receivedCount.incrementAndGet();
                        done.countDown();
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }
            });

            if (i<=maxRedeliveries) {
                assertTrue("listener done", done.await(5, TimeUnit.SECONDS));
            } else {
                // final redlivery gets poisoned before dispatch
                assertFalse("listener done", done.await(1, TimeUnit.SECONDS));
            }
            connection.close();
            connections.remove(connection);
        }

        // We should be able to get the message off the DLQ now.
        TextMessage m = (TextMessage)dlqConsumer.receive(1000);
        assertNotNull("Got message from DLQ", m);
        assertEquals("1st", m.getText());
        String cause = m.getStringProperty(ActiveMQMessage.DLQ_DELIVERY_FAILURE_CAUSE_PROPERTY);
        LOG.info("cause: " + cause);
        assertTrue("cause exception has policy ref", cause.contains("RedeliveryPolicy"));
        assertTrue("cause exception has redelivered count ref: " + cause, cause.contains("[5]"));

        dlqSession.commit();

    }

};