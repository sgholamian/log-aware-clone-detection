//,temp,ExpiredMessagesTest.java,378,385,temp,ExpiredMessagesTest.java,293,304
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                DestinationStatistics view = getDestinationStatistics(broker, destination);
                LOG.info("Stats: size: " + view.getMessages().getCount() + ", enqueues: "
                        + view.getEnqueues().getCount() + ", dequeues: "
                        + view.getDequeues().getCount() + ", dispatched: "
                        + view.getDispatched().getCount() + ", inflight: "
                        + view.getInflight().getCount() + ", expiries: "
                        + view.getExpired().getCount());

                return view.getMessages().getCount() == 0;
            }

};