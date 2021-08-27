//,temp,FailoverTransactionTest.java,548,558,temp,FailoverTransactionTest.java,219,229
//,3
public class xxx {
            public void run() {
                LOG.info("doing async send...");
                try {
                    produceMessage(session, destination);
                } catch (JMSException e) {
                    //assertTrue(e instanceof TransactionRolledBackException);
                    LOG.info("got send exception: ", e);
                }
                sendDoneLatch.countDown();
                LOG.info("done async send");
            }

};