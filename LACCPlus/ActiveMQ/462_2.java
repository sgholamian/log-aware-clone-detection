//,temp,RedeliveryPolicyTest.java,917,1000,temp,RedeliveryPolicyTest.java,806,905
//,3
public class xxx {
    public void testRepeatedRedeliveryNoCommitForwardMessage() throws Exception {

        connection.start();
        Session dlqSession = connection.createSession(true, Session.SESSION_TRANSACTED);
        ActiveMQQueue destination = new ActiveMQQueue("TEST");
        MessageProducer producer = dlqSession.createProducer(destination);

        // Send the messages
        producer.send(dlqSession.createTextMessage("1st"));

        dlqSession.commit();
        MessageConsumer dlqConsumer = dlqSession.createConsumer(new ActiveMQQueue("ActiveMQ.DLQ"));

        final MessageProducer forwardingProducer = dlqSession.createProducer(new ActiveMQQueue("TEST_FORWARD"));

        // Send the messages

        final int maxRedeliveries = 2;
        final AtomicInteger receivedCount = new AtomicInteger(0);

        for (int i=0;i<=maxRedeliveries+1;i++) {
            connection = (ActiveMQConnection)factory.createConnection(userName, password);
            connections.add(connection);

            RedeliveryPolicy policy = connection.getRedeliveryPolicy();
            policy.setInitialRedeliveryDelay(0);
            policy.setUseExponentialBackOff(false);
            policy.setMaximumRedeliveries(maxRedeliveries);

            connection.start();
            final CountDownLatch done = new CountDownLatch(1);

            final ActiveMQSession session = (ActiveMQSession) connection.createSession(true, Session.SESSION_TRANSACTED);
            session.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        ActiveMQTextMessage m = (ActiveMQTextMessage) message;
                        LOG.info("Got: " + ((ActiveMQTextMessage) message).getMessageId() + ", seq:" + ((ActiveMQTextMessage) message).getMessageId().getBrokerSequenceId() + " ,Redelivery: " + m.getRedeliveryCounter());
                        assertEquals("1st", m.getText());
                        assertEquals(receivedCount.get(), m.getRedeliveryCounter());
                        receivedCount.incrementAndGet();

                        // do a forward of the received message, will reset the messageID
                        forwardingProducer.send(message);
                        done.countDown();
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }
            });

            connection.createConnectionConsumer(
                    destination,
                    null,
                    new ServerSessionPool() {
                        @Override
                        public ServerSession getServerSession() throws JMSException {
                            return new ServerSession() {
                                @Override
                                public Session getSession() throws JMSException {
                                    return session;
                                }

                                @Override
                                public void start() throws JMSException {
                                }
                            };
                        }
                    },
                    100,
                    false);

            Wait.waitFor(new Wait.Condition() {
                @Override
                public boolean isSatisified() throws Exception {
                    session.run();
                    return done.await(10, TimeUnit.MILLISECONDS);
                }
            }, 5000);

            if (i<=maxRedeliveries) {
                assertTrue("listener done @" + i, done.await(5, TimeUnit.SECONDS));
            } else {
                // final redelivery gets poisoned before dispatch
                assertFalse("listener not done @" + i, done.await(5, TimeUnit.SECONDS));
            }
            connection.close();
            connections.remove(connection);
        }

        // We should be able to get the message off the DLQ now.
        TextMessage m = (TextMessage)dlqConsumer.receive(1000);
        assertNotNull("Got message from DLQ", m);
        assertEquals("1st", m.getText());
        String cause = m.getStringProperty(ActiveMQMessage.DLQ_DELIVERY_FAILURE_CAUSE_PROPERTY);
        assertTrue("cause exception has policy ref", cause.contains("RedeliveryPolicy"));
        dlqSession.commit();

    }

};