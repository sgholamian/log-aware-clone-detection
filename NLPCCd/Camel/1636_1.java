//,temp,sample_2319.java,2,17,temp,sample_2320.java,2,17
//,3
public class xxx {
public void dummy_method(){
consumers.returnObject(consumer);
producer.getMessageProducer().send(request);
try {
releaseProducerCallback.release(producer);
} catch (Exception exception) {
}
try {
responseObject = messageExchanger.exchange(null, getResponseTimeOut(), TimeUnit.MILLISECONDS);
EXCHANGERS.remove(correlationId);
} catch (InterruptedException e) {


log.info("exchanger was interrupted while waiting on response");
}
}

};