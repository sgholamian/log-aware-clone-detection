//,temp,FailoverConsumerUnconsumedTest.java,285,293,temp,FailoverConsumerUnconsumedTest.java,161,169
//,3
public class xxx {
            public boolean isSatisified() throws Exception {
                int totalDelivered = 0;
                for (TestConsumer testConsumer : testConsumers) {
                    long delivered = testConsumer.deliveredSize();
                    LOG.info(testConsumer.getConsumerId() + " delivered: " + delivered);
                    totalDelivered += delivered;
                }
                return totalDelivered == maxConsumers * prefetch;
            }

};