//,temp,AMQDeadlockTest3.java,300,313,temp,MDBTest.java,339,348
//,3
public class xxx {
        @Override
        public void onMessage(Message msg) {

            try {
                LOG.info("Listener1 Consumed message " + msg.getIntProperty("count"));
                messageCount.incrementAndGet();
                doneLatch.countDown();
                Thread.sleep(waitTime);
            } catch (JMSException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

};