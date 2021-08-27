//,temp,FailoverTransactionTest.java,1135,1146,temp,AMQ3932Test.java,144,152
//,3
public class xxx {
            public void run() {
                try {
                    started.countDown();
                    LOG.info("Entering into a timed Sync receive call");
                    consumer.receive(10);
                } catch (JMSException e) {
                }
                done.countDown();
            }

};