//,temp,ThreeBrokerTempDestDemandSubscriptionCleanupTest.java,133,137,temp,NonBlockingConsumerRedeliveryTest.java,415,419
//,3
public class xxx {
                @Override
                public boolean isSatisified() throws Exception {
                    LOG.info("Consumer has received " + dlqed.size() + " messages in DLQ.");
                    return dlqed.size() == MSG_COUNT;
                }

};