//,temp,ExpiredMessagesWithNoConsumerTest.java,332,339,temp,ExpiredMessagesWithNoConsumerTest.java,168,182
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                try {
                LOG.info("enqueue=" + view.getEnqueueCount() + ", dequeue=" + view.getDequeueCount()
                        + ", inflight=" + view.getInFlightCount() + ", expired= " + view.getExpiredCount()
                        + ", size= " + view.getQueueSize());
                return view.getDequeueCount() != 0
                        && view.getDequeueCount() == view.getExpiredCount()
                        && view.getDequeueCount() == view.getEnqueueCount()
                        && view.getQueueSize() == 0;
                } catch (Exception ignored) {
                    LOG.info(ignored.toString());
                }
                return false;
            }

};