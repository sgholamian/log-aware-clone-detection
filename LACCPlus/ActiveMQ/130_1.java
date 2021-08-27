//,temp,FailoverConsumerUnconsumedTest.java,334,342,temp,FailoverConsumerUnconsumedTest.java,208,216
//,3
public class xxx {
            public boolean isSatisified() throws Exception {
                int totalUnconsumed = 0;
                for (TestConsumer testConsumer : testConsumers) {
                    long unconsumed = testConsumer.unconsumedSize();
                    LOG.info(testConsumer.getConsumerId() + " after restart: unconsumed: " + unconsumed);
                    totalUnconsumed += unconsumed;
                }
                return totalUnconsumed == (maxConsumers) * prefetch;
            }

};