//,temp,FailoverConsumerUnconsumedTest.java,285,293,temp,FailoverConsumerUnconsumedTest.java,161,169
//,3
public class xxx {
            public boolean isSatisified() throws Exception {
                int totalUnconsumed = 0;
                for (TestConsumer testConsumer : testConsumers) {
                    long unconsumed = testConsumer.unconsumedSize();
                    LOG.info(testConsumer.getConsumerId() + " unconsumed: " + unconsumed);
                    totalUnconsumed += unconsumed;
                }
                return totalUnconsumed == (maxConsumers-1) * prefetch;
            }

};