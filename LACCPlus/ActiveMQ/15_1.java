//,temp,NonBlockingConsumerRedeliveryTest.java,263,267,temp,AMQ7077Test.java,114,119
//,3
public class xxx {
                @Override
                public boolean isSatisified() throws Exception {
                    LOG.info("Consumer has received " + received.size() + " messages.");
                    return received.size() == MSG_COUNT;
                }

};