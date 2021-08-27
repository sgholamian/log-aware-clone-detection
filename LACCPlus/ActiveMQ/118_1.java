//,temp,FailoverConsumerUnconsumedTest.java,146,153,temp,FailoverPrefetchZeroTest.java,95,102
//,3
public class xxx {
            @Override
            public void onMessage(Message message) {
                try {
                    LOG.info("onMessage:" + message.getJMSMessageID());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

};