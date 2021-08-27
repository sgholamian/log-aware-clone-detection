//,temp,NetworkFailoverTest.java,108,116,temp,JMSClientTransactionTest.java,224,231
//,3
public class xxx {
            @Override
            public void onMessage(Message message) {
                try {
                    LOG.info("dlq " + message.getJMSMessageID());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
                remoteDLQCount.incrementAndGet();
            }

};