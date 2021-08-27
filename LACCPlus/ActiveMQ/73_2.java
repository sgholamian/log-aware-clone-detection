//,temp,AMQ6059Test.java,92,100,temp,ExpiredMessagesWithNoConsumerTest.java,241,247
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("enqueue=" + view.getEnqueueCount() + ", dequeue=" + view.getDequeueCount()
                        + ", inflight=" + view.getInFlightCount() + ", expired= " + view.getExpiredCount()
                        + ", size= " + view.getQueueSize());
                return sendCount == view.getExpiredCount();
            }

};