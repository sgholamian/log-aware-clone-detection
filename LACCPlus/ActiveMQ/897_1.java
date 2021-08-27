//,temp,PublishOnTopicConsumedMessageTest.java,37,50,temp,FailoverTxSlowAckTest.java,102,120
//,3
public class xxx {
    public synchronized void onMessage(Message message) {

        // lets resend the message somewhere else
        try {
            Message msgCopy = (Message)((org.apache.activemq.command.Message)message).copy();
            replyProducer.send(msgCopy);

            // log.info("Sending reply: " + message);
            super.onMessage(message);
        } catch (JMSException e) {
            LOG.info("Failed to send message: " + e);
            e.printStackTrace();
        }
    }

};