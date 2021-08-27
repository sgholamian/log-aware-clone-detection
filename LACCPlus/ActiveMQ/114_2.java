//,temp,XAConsumerTest.java,104,113,temp,AMQ3932Test.java,86,94
//,3
public class xxx {
            public void run() {
                try {
                    started.countDown();
                    LOG.info("Entering into a Sync receive call");
                    consumer.receive();
                } catch (JMSException e) {
                }
                done.countDown();
            }

};