//,temp,MemMessageIdList.java,96,113,temp,MessageIdList.java,103,130
//,3
public class xxx {
    @Override
    public void onMessage(Message message) {
        String id = null;
        try {
            id = message.getJMSMessageID();
            synchronized (semaphore) {
                messageIds.add(id);
                semaphore.notifyAll();
            }
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Received message: " + message);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
        if (parent != null) {
            parent.onMessage(message);
        }
        if (processingDelay > 0) {
            try {
                Thread.sleep(processingDelay);
            } catch (InterruptedException e) {
            }
        }
    }

};