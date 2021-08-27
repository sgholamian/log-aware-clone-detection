//,temp,DurableConsumerCloseAndReconnectTcpTest.java,176,188,temp,DurableConsumerCloseAndReconnectTcpTest.java,85,95
//,3
public class xxx {
    public void onException(IOException error) {
       LOG.info("Transport listener exception:" + error);
       if (reconnectInTransportListener) {
           try {
               TimeUnit.MILLISECONDS.sleep(500);
               makeConsumer();
           } catch (Exception e) {
               reconnectException = e;
           }
       
           gotException.countDown();
       }
    }

};