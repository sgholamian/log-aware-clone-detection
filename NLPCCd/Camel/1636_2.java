//,temp,sample_2319.java,2,17,temp,sample_2320.java,2,17
//,3
public class xxx {
public void dummy_method(){
try {
releaseProducerCallback.release(producer);
} catch (Exception exception) {
}
try {
responseObject = messageExchanger.exchange(null, getResponseTimeOut(), TimeUnit.MILLISECONDS);
EXCHANGERS.remove(correlationId);
} catch (InterruptedException e) {
exchange.setException(e);
} catch (TimeoutException e) {


log.info("exchanger timed out while waiting on response");
}
}

};