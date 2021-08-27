//,temp,FailoverConsumerUnconsumedTest.java,180,187,temp,FailoverTransactionTest.java,741,748
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