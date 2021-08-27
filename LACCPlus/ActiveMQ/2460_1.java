//,temp,FailoverDurableSubTransactionTest.java,234,348,temp,FailoverDurableSubTransactionTest.java,129,232
//,3
public class xxx {
    @org.junit.Test
    public void testFailoverCommitListener() throws Exception {

        final AtomicInteger dispatchedCount = new AtomicInteger(0);
        final int errorAt = FailType.ON_ACK.equals(failType) ? 1 : 1;
        final int messageCount = 10;
        broker = createBroker(true);

        broker.setPlugins(new BrokerPlugin[]{
                new BrokerPluginSupport() {
                    @Override
                    public void commitTransaction(ConnectionContext context, TransactionId xid, boolean onePhase) throws Exception {
                        LOG.info("commit request: " + xid);
                        if (FailType.ON_COMMIT.equals(failType) && dispatchedCount.incrementAndGet() == errorAt) {
                            for (TransportConnection transportConnection : broker.getTransportConnectors().get(0).getConnections()) {
                                LOG.error("Whacking connection on commit: " + transportConnection);
                                transportConnection.serviceException(new IOException("ERROR NOW"));
                            }
                        } else {
                            super.commitTransaction(context, xid, onePhase);
                        }
                    }


                    @Override
                    public void acknowledge(ConsumerBrokerExchange consumerExchange, MessageAck ack) throws Exception {
                        LOG.info("ack request: " + ack);
                        if (FailType.ON_ACK.equals(failType) /*&& ack.getAckType() == MessageAck.DELIVERED_ACK_TYPE*/ && dispatchedCount.incrementAndGet() == errorAt) {
                            for (TransportConnection transportConnection : broker.getTransportConnectors().get(0).getConnections()) {
                                LOG.error("Whacking connection on ack: " + transportConnection);
                                transportConnection.serviceException(new IOException("ERROR NOW"));
                            }
                        } else {
                            super.acknowledge(consumerExchange, ack);
                        }
                    }
                }

        });
        broker.start();

        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("failover:(" + url + ")");
        cf.setAlwaysSyncSend(true);
        cf.setAlwaysSessionAsync(true);
        //cf.getPrefetchPolicy().setDurableTopicPrefetch(FailType.ON_ACK.equals(failType) ? 2 : 100);
        cf.setWatchTopicAdvisories(false);
        Connection connection = cf.createConnection();
        connection.setClientID("CID");
        connection.start();
        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
        Topic destination = session.createTopic(TOPIC_NAME);

        MessageConsumer consumer = session.createDurableSubscriber(destination, "DS");
        consumer.close();
        connection.close();

        produceMessage(destination, messageCount*2);
        LOG.info("Production done! " + broker.getDestination(ActiveMQDestination.transform(destination)));


        connection = cf.createConnection();
        connection.setClientID("CID");
        connection.start();
        final Session receiveSession = connection.createSession(true, Session.SESSION_TRANSACTED);
        consumer = receiveSession.createDurableSubscriber(destination, "DS");

        final AtomicBoolean success = new AtomicBoolean(false);
        final HashSet<Integer> dupCheck = new HashSet<Integer>();
        final AtomicInteger receivedCount = new AtomicInteger();
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message msg) {
                    try {
                        int i = receivedCount.getAndIncrement();
                        LOG.info("Received: @" + i + ":" + msg.getJMSMessageID() + ", ID:" + msg.getIntProperty("ID"));
                        assertTrue("single instance of: " +  i, dupCheck.add( msg.getIntProperty("ID")));

                        if (receivedCount.get() == messageCount) {
                            receiveSession.commit();
                            success.set(true);
                        }
                    } catch (TransactionRolledBackException expected) {
                        LOG.info("Got expected", expected);
                        try {
                            receiveSession.rollback();
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                        dupCheck.clear();
                        receivedCount.set(0);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
            }
        });
        connection.start();

        try {

            assertTrue("committed ok", Wait.waitFor(new Wait.Condition() {
                @Override
                public boolean isSatisified() throws Exception {
                    return success.get();
                }
            }));
        } finally {
            consumer.close();
            connection.close();
        }

        org.apache.activemq.broker.region.Destination dlq = broker.getDestination(ActiveMQDestination.transform(new ActiveMQQueue(DEFAULT_DEAD_LETTER_QUEUE_NAME)));
        LOG.info("DLQ: " + dlq);
        assertEquals("DLQ empty ", 0, dlq.getDestinationStatistics().getMessages().getCount());

    }

};