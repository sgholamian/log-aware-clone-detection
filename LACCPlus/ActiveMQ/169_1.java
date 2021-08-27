//,temp,PublishOnQueueConsumedMessageInTransactionTest.java,116,135,temp,VirtualTopicDLQTest.java,377,400
//,3
public class xxx {
    public synchronized void onMessage(Message m) {
        try {
            objectMessage = (ObjectMessage)m;
            consumeMessage(objectMessage, messages);

            LOG.info("consumer received message :" + objectMessage);
            consumerSession.commit();

        } catch (Exception e) {
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