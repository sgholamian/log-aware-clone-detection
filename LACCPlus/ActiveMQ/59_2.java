//,temp,FailoverTransactionTest.java,1074,1085,temp,AMQ3932Test.java,115,123
//,3
public class xxx {
            public void run() {
                try {
                    started.countDown();
                    LOG.info("Entering into a Sync receiveNoWait call");
                    consumer.receiveNoWait();
                } catch (JMSException e) {
                }
                done.countDown();
            }

};