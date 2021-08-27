//,temp,ExpiredMessagesWithNoConsumerTest.java,332,339,temp,ExpiredMessagesWithNoConsumerTest.java,168,182
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("enqueue=" + view.getEnqueueCount() + ", dequeue=" + view.getDequeueCount()
                        + ", inflight=" + view.getInFlightCount() + ", expired= " + view.getExpiredCount()
                        + ", size= " + view.getQueueSize());

                return view.getExpiredCount() > 0 && (view.getEnqueueCount() - view.getInFlightCount()) == view.getExpiredCount();
            }

};