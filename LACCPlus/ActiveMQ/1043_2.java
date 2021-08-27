//,temp,FailoverXATransactionTest.java,154,210,temp,FailoverXATransactionTest.java,94,152
//,3
public class xxx {
    @org.junit.Test
    public void testFailoverSendPrepareReplyLost() throws Exception {

        broker = createBroker(true);

        final AtomicBoolean first = new AtomicBoolean(false);
        broker.setPlugins(new BrokerPlugin[]{
                new BrokerPluginSupport() {
                    @Override
                    public int prepareTransaction(final ConnectionContext context,
                                                  TransactionId xid) throws Exception {
                        int result = super.prepareTransaction(context, xid);
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

                        return result;
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
            session.getXAResource().rollback(xid);
        } catch (Exception expected) {
            expected.printStackTrace();
        }

        connection.close();

        assertEquals(0, broker.getAdminView().getTotalMessageCount());
    }

};