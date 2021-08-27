//,temp,FailoverXATransactionTest.java,154,210,temp,FailoverXATransactionTest.java,94,152
//,3
public class xxx {
    @org.junit.Test
    public void testFailoverSendCommitReplyLost() throws Exception {

        broker = createBroker(true);

        final AtomicBoolean first = new AtomicBoolean(false);
        broker.setPlugins(new BrokerPlugin[]{
                new BrokerPluginSupport() {
                    @Override
                    public void commitTransaction(final ConnectionContext context,
                                                  TransactionId xid, boolean onePhase) throws Exception {
                        super.commitTransaction(context, xid, onePhase);
                        if (first.compareAndSet(false, true)) {
                            context.setDontSendReponse(true);
                            Executors.newSingleThreadExecutor().execute(new Runnable() {
                                public void run() {
                                    LOG.info("Stopping broker on prepare");
                                    try {
                                        context.getConnection().stop();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                }
        });
        broker.start();

        ActiveMQXAConnectionFactory cf = new ActiveMQXAConnectionFactory("failover:(" + url + ")");
        XAConnection connection = cf.createXAConnection();
        connection.start();
        final XASession session = connection.createXASession();
        Queue destination = session.createQueue(QUEUE_NAME);

        Xid xid = TestUtils.createXid();
        session.getXAResource().start(xid, XAResource.TMNOFLAGS);
        produceMessage(session, destination);
        session.getXAResource().end(xid, XAResource.TMSUCCESS);

        try {
            session.getXAResource().prepare(xid);
        } catch (Exception expected) {
            expected.printStackTrace();
        }

        try {
            session.getXAResource().commit(xid, false);
        } catch (Exception expected) {
            expected.printStackTrace();
        }

        connection.close();

        assertEquals(1, broker.getAdminView().getTotalMessageCount());
    }

};