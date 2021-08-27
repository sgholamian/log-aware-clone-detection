//,temp,FailoverDurableSubTransactionTest.java,234,348,temp,FailoverDurableSubTransactionTest.java,129,232
//,3
public class xxx {
    @org.junit.Test
    public void testFailoverCommit() throws Exception {

        final AtomicInteger dispatchedCount = new AtomicInteger(0);
        final int errorAt = FailType.ON_COMMIT.equals(failType) ? 1 : 9;
        final int messageCount = 10;
        broker = createBroker(true);

        broker.setPlugins(new BrokerPlugin[]{
                new BrokerPluginSupport() {
                    @Override
                    public void commitTransaction(ConnectionContext context, TransactionId xid, boolean onePhase) throws Exception {
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
                        if (FailType.ON_ACK.equals(failType) && ack.getAckType() == MessageAck.DELIVERED_ACK_TYPE && dispatchedCount.incrementAndGet() == errorAt) {
                            for (TransportConnection transportConnection : broker.getTransportConnectors().get(0).getConnections()) {
                                LOG.error("Whacking connection on ack: " + transportConnection);
                                transportConnection.serviceException(new IOException("ERROR NOW"));
                            }
                        }
                        super.acknowledge(consumerExchange, ack);
                    }

                    @Override
                    public void postProcessDispatch(MessageDispatch messageDispatch) {
                        super.postProcessDispatch(messageDispatch);
                        if ((FailType.ON_DISPATCH.equals(failType) || FailType.ON_DISPACH_WITH_REPLAY_DELAY.equals(failType)) && dispatchedCount.incrementAndGet() == errorAt) {
                            for (TransportConnection transportConnection : broker.getTransportConnectors().get(0).getConnections()) {
                                LOG.error("Whacking connection on dispatch: " + transportConnection);
                                transportConnection.serviceException(new IOException("ERROR NOW"));
                            }
                        }
                    }
                }
        });
        broker.start();

        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("failover:(" + url + ")");
        cf.setAlwaysSyncSend(true);
        cf.setAlwaysSessionAsync(false);
        cf.getPrefetchPolicy().setDurableTopicPrefetch(FailType.ON_ACK.equals(failType) ? 2 : 100);
        configureConnectionFactory(cf);
        Connection connection = cf.createConnection();
        connection.setClientID("CID");
        connection.start();
        final Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
        Topic destination = session.createTopic(TOPIC_NAME);

        MessageConsumer consumer = session.createDurableSubscriber(destination, "DS");
        consumer.close();

        produceMessage(destination, messageCount);
        LOG.info("Production done! " + broker.getDestination(ActiveMQDestination.transform(destination)));


        consumer = session.createDurableSubscriber(destination, "DS");

        AtomicBoolean success = new AtomicBoolean(false);

        HashSet<Integer> dupCheck = new HashSet<Integer>();
        while (!success.get()) {
            dupCheck.clear();
            int i = 0;
            for (i = 0; i < messageCount; i++) {
                Message msg = consumer.receive(5000);
                if (msg == null) {
                    LOG.info("Failed to receive on: " + i);
                    break;
                }
                LOG.info("Received: @" + i + ":" + msg.getJMSMessageID() + ", ID:" + msg.getIntProperty("ID"));
                assertTrue("single instance of: " +  i, dupCheck.add( msg.getIntProperty("ID")));
            }

            try {
                if (i == messageCount) {
                    session.commit();
                    success.set(true);
                } else {
                    session.rollback();
                }
            } catch (TransactionRolledBackException expected) {
                LOG.info("Got expected", expected);
                session.rollback();
            }
        }

        consumer.close();
        connection.close();

        org.apache.activemq.broker.region.Destination dlq = broker.getDestination(ActiveMQDestination.transform(new ActiveMQQueue(DEFAULT_DEAD_LETTER_QUEUE_NAME)));
        LOG.info("DLQ: " + dlq);
        assertEquals("DLQ empty ", 0, dlq.getDestinationStatistics().getMessages().getCount());

    }

};