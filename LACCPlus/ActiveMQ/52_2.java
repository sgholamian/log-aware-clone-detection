//,temp,DurableConsumerCloseAndReconnectTcpTest.java,161,172,temp,JMSClientTest.java,1339,1347
//,3
public class xxx {
            @Override
            public void onMessage(Message message) {
                try {
                    message.acknowledge();
                    done.countDown();
                } catch (JMSException ex) {
                    LOG.info("Caught exception.", ex);
                }
            }

};