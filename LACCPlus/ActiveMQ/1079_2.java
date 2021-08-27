//,temp,RedeliveryPolicyTest.java,642,709,temp,RedeliveryPolicyTest.java,467,547
//,3
public class xxx {
    public void testRepeatedRedeliveryBrokerCloseReceiveNoCommit() throws Exception {

        connection.start();
        ActiveMQQueue destination = new ActiveMQQueue("TEST");
        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);

        MessageProducer producer = session.createProducer(destination);

        // Send the messages
        producer.send(session.createTextMessage("1st"));

        session.commit();

        final int maxRedeliveries = 4;
        for (int i=0;i<=maxRedeliveries +1;i++) {

            final ActiveMQConnection consumerConnection = (ActiveMQConnection)factory.createConnection(userName, password);
            connections.add(consumerConnection);
            // Receive a message with the JMS API
            RedeliveryPolicy policy = consumerConnection.getRedeliveryPolicy();
            policy.setInitialRedeliveryDelay(0);
            policy.setUseExponentialBackOff(false);
            policy.setMaximumRedeliveries(maxRedeliveries);

            consumerConnection.start();
            session = consumerConnection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(destination);

            ActiveMQTextMessage m = ((ActiveMQTextMessage)consumer.receive(4000));
            if (i<=maxRedeliveries) {
                assertEquals("1st", m.getText());
                assertEquals(i, m.getRedeliveryCounter());
            } else {
                assertNull("null on exceeding redelivery count", m);

                assertTrue("message in dlq", Wait.waitFor(new Wait.Condition() {
                    @Override
                    public boolean isSatisified() throws Exception {
                        LOG.info("total dequeue count: " + broker.getAdminView().getTotalDequeueCount());
                        return broker.getAdminView().getTotalDequeueCount() == 1;
                    }
                }));
            }

            // abortive close via broker
            for (VMTransportServer transportServer : VMTransportFactory.SERVERS.values()) {
                transportServer.stop();
            }

            Wait.waitFor(new Wait.Condition() {
                @Override
                public boolean isSatisified() throws Exception {
                    return consumerConnection.isTransportFailed();
                }
            });

            try {
                consumerConnection.close();
            } catch (Exception expected) {
            } finally {
                connections.remove(consumerConnection);
            }
        }

        connection = (ActiveMQConnection)factory.createConnection(userName, password);
        connection.start();
        connections.add(connection);
        Session dlqSession = connection.createSession(true, Session.SESSION_TRANSACTED);
        MessageConsumer dlqConsumer = dlqSession.createConsumer(new ActiveMQQueue("ActiveMQ.DLQ"));

        // We should be able to get the message off the DLQ now.
        TextMessage m = (TextMessage)dlqConsumer.receive(1000);
        assertNotNull("Got message from DLQ", m);
        assertEquals("1st", m.getText());
        String cause = m.getStringProperty(ActiveMQMessage.DLQ_DELIVERY_FAILURE_CAUSE_PROPERTY);
        assertTrue("cause exception has policy ref: " + cause, cause.contains("RedeliveryPolicy"));
        assertTrue("cause exception has pre dispatch and count:" + cause, cause.contains("[5]"));

        dlqSession.commit();

    }

};