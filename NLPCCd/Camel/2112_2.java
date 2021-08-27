//,temp,sample_6135.java,2,19,temp,sample_6227.java,2,19
//,2
public class xxx {
public void dummy_method(){
final Producer producer;
final Endpoint endpoint;
Object recipient = null;
try {
recipient = expression.evaluate(exchange, Object.class);
endpoint = resolveEndpoint(exchange, recipient);
producer = producerCache.acquireProducer(endpoint);
} catch (Throwable e) {
if (isIgnoreInvalidEndpoint()) {
if (LOG.isDebugEnabled()) {


log.info("endpoint uri is invalid this exception will be ignored");
}
}
}
}

};