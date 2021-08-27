//,temp,NonBlockingConsumerRedeliveryTest.java,348,352,temp,NonBlockingConsumerRedeliveryTest.java,209,213
//,2
public class xxx {
                @Override
                public boolean isSatisified() throws Exception {
                    LOG.info("Consumer has received " + received.size() + " messages.");
                    return received.size() == MSG_COUNT;
                }

};