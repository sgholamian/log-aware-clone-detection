//,temp,FailoverDuplicateTest.java,162,173,temp,FailoverTransactionTest.java,423,434
//,3
public class xxx {
            public void run() {
                LOG.info("doing async send...");
                try {
                    produceMessage(session, destination);
                } catch (JMSException e) {
                    //assertTrue(e instanceof TransactionRolledBackException);
                    LOG.error("got send exception: ", e);
                    fail("got unexpected send exception" + e);
                }
                sendDoneLatch.countDown();
                LOG.info("done async send");
            }

};