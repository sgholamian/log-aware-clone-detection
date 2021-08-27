//,temp,FailoverTxSlowAckTest.java,213,224,temp,FailoverConsumerOutstandingCommitTest.java,166,176
//,3
public class xxx {
            public void run() {
                LOG.info("producer started");
                try {
                    produceMessage(producerSession, destination, prefetch * 2);
                } catch (javax.jms.IllegalStateException SessionClosedExpectedOnShutdown) {
                } catch (JMSException e) {
                    e.printStackTrace();
                    fail("unexpceted ex on producer: " + e);
                }
                LOG.info("producer done");
            }

};