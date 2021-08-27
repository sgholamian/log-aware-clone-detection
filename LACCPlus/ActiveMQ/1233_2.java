//,temp,NonBlockingConsumerRedeliveryTest.java,308,312,temp,NonBlockingConsumerRedeliveryTest.java,85,89
//,2
public class xxx {
                @Override
                public boolean isSatisified() throws Exception {
                    LOG.info("Consumer has received " + received.size() + " messages.");
                    return received.size() == MSG_COUNT;
                }

};