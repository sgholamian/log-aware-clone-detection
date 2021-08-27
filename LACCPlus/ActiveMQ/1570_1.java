//,temp,MemMessageIdList.java,96,113,temp,MessageIdList.java,103,130
//,3
public class xxx {
    public void onMessage(Message message) {
        String id = null;
        try {
            id = message.getJMSMessageID();
            synchronized (semaphore) {
                messageIds.add(id);
                semaphore.notifyAll();
            }
            if (verbose) {
                LOG.info("Received message: " + message);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
        if (parent != null) {
            parent.onMessage(message);
        }
    }

};