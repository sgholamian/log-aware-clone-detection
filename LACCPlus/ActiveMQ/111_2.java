//,temp,FailoverPrefetchZeroTest.java,125,137,temp,FailoverConsumerOutstandingCommitTest.java,148,161
//,3
public class xxx {
            public void onMessage(Message message) {
                LOG.info("consume one and commit");

                assertNotNull("got message", message);

                try {
                    consumerSession.commit();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
                commitDoneLatch.countDown();
                messagesReceived.countDown();
                LOG.info("done commit");
            }

};