//,temp,NonBlockingConsumerRedeliveryTest.java,225,229,temp,NonBlockingConsumerRedeliveryTest.java,141,145
//,3
public class xxx {
                @Override
                public boolean isSatisified() throws Exception {
                    LOG.info("Consumer has received " + received.size() + " messages.");
                    return received.size() == MSG_COUNT;
                }

};