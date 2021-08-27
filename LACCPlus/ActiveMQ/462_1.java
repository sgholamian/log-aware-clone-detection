//,temp,RedeliveryPolicyTest.java,917,1000,temp,RedeliveryPolicyTest.java,806,905
//,3
public class xxx {
    public void redeliveryRollbackWithDelay(final boolean blockingRedelivery) throws Exception {

        connection.start();
        Session sendSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        ActiveMQQueue destination = new ActiveMQQueue("TEST");
        MessageProducer producer = sendSession.createProducer(destination);
        producer.send(sendSession.createTextMessage("1st"));
        producer.send(sendSession.createTextMessage("2nd"));


        connection = (ActiveMQConnection)factory.createConnection(userName, password);
        connections.add(connection);

        RedeliveryPolicy policy = connection.getRedeliveryPolicy();
        policy.setInitialRedeliveryDelay(2000);
        policy.setUseExponentialBackOff(false);
        connection.setNonBlockingRedelivery(blockingRedelivery);
        connection.start();
        final CountDownLatch done = new CountDownLatch(3);

        final ActiveMQSession session = (ActiveMQSession) connection.createSession(true, Session.SESSION_TRANSACTED);
        final List<String> list = new ArrayList<>();
        session.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    ActiveMQTextMessage m = (ActiveMQTextMessage) message;
                    LOG.info("Got: " + ((ActiveMQTextMessage) message).getMessageId() + ", seq:" + ((ActiveMQTextMessage) message).getMessageId().getBrokerSequenceId());
                    list.add(((ActiveMQTextMessage) message).getText());
                    if (done.getCount() == 3)
                    {
                        session.rollback();
                    }
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

        connection.close();
        connections.remove(connection);

        assertEquals(list.size(), 3);
        if (blockingRedelivery) {
            assertEquals("1st", list.get(0));
            assertEquals("2nd", list.get(1));
            assertEquals("1st", list.get(2));
        } else {
            assertEquals("1st", list.get(0));
            assertEquals("1st", list.get(1));
            assertEquals("2nd", list.get(2));
        }
    }

};