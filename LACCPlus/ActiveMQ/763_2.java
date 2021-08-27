//,temp,AMQDeadlockTest3.java,300,313,temp,MDBTest.java,339,348
//,3
public class xxx {
            public void onMessage(Message message) {
                LOG.info("Message:" + message);
                super.onMessage(message);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                messageDelivered.countDown();
            };

};