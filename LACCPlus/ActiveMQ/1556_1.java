//,temp,TransactionTest.java,73,94,temp,QueueRepeaterTest.java,72,92
//,3
public class xxx {
            @Override
            public void onMessage(Message m) {
                try {
                    TextMessage tm = (TextMessage)m;
                    receivedText = tm.getText();
                    latch.countDown();

                    LOG.info("consumer received message :" + receivedText);
                    consumerSession.commit();
                    LOG.info("committed transaction");
                } catch (JMSException e) {
                    try {
                        consumerSession.rollback();
                        LOG.info("rolled back transaction");
                    } catch (JMSException e1) {
                        LOG.info(e1.toString());
                        e1.printStackTrace();
                    }
                    LOG.info(e.toString());
                    e.printStackTrace();
                }
            }

};