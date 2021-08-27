//,temp,XAConsumerTest.java,104,113,temp,AMQ3932Test.java,86,94
//,3
public class xxx {
                public void run() {
                    try {
                        messageConsumer.receive(600000);
                    } catch (JMSException expected) {
                        receiveLatch.countDown();
                        LOG.info("got expected ex: ", expected);
                    } finally {
                        receiveThreadDone.countDown();
                    }
                }

};