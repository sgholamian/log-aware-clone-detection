//,temp,NonBlockingConsumerRedeliveryTest.java,155,159,temp,TwoBrokerQueueSendReceiveTest.java,51,55
//,3
public class xxx {
                @Override
                public boolean isSatisified() throws Exception {
                    LOG.info("Consumer has received " + received.size() + " messages since rollback.");
                    return received.size() == MSG_COUNT;
                }

};