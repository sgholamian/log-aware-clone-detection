//,temp,AMQ6059Test.java,92,100,temp,ExpiredMessagesWithNoConsumerTest.java,241,247
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("DLQ stats: Enqueues {}, Dispatches {}, Expired {}, Inflight {}",
                    new Object[] { queueViewMBean.getEnqueueCount(),
                                   queueViewMBean.getDispatchCount(),
                                   queueViewMBean.getExpiredCount(),
                                   queueViewMBean.getInFlightCount()});
                return queueViewMBean.getEnqueueCount() == 1;
            }

};