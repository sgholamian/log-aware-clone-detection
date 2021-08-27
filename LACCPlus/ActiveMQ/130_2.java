//,temp,FailoverConsumerUnconsumedTest.java,334,342,temp,FailoverConsumerUnconsumedTest.java,208,216
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