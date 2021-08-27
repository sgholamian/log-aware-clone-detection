//,temp,ExpiredMessagesTest.java,378,385,temp,ExpiredMessagesTest.java,293,304
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                long oldEnqueues = view.getEnqueues().getCount();
                Thread.sleep(200);
                LOG.info("Stats: received: "  + received.get() + ", size= " + view.getMessages().getCount() + ", enqueues: " + view.getDequeues().getCount() + ", dequeues: " + view.getDequeues().getCount()
                        + ", dispatched: " + view.getDispatched().getCount() + ", inflight: " + view.getInflight().getCount() + ", expiries: " + view.getExpired().getCount());
                return oldEnqueues == view.getEnqueues().getCount();
            }

};