//,temp,FailoverTransactionTest.java,548,558,temp,FailoverTransactionTest.java,219,229
//,3
public class xxx {
            public void run() {
                LOG.info("doing async commit...");
                try {
                    session.commit();
                } catch (JMSException e) {
                    assertTrue(e instanceof TransactionRolledBackException);
                    LOG.info("got commit exception: ", e);
                }
                commitDoneLatch.countDown();
                LOG.info("done async commit");
            }

};