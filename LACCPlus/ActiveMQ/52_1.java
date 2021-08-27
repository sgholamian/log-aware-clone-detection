//,temp,DurableConsumerCloseAndReconnectTcpTest.java,161,172,temp,JMSClientTest.java,1339,1347
//,3
public class xxx {
    public void onException(JMSException exception) {
        LOG.info("Exception listener exception:" + exception);
        if (reconnectInExceptionListener) {
            try {
                makeConsumer();
            } catch (Exception e) {
                reconnectException = e;
            }
        
            gotException.countDown();
        }
    }

};