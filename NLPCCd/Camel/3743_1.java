//,temp,sample_6563.java,2,13,temp,sample_1015.java,2,11
//,3
public class xxx {
public void act(final Client client, final Exchange exchange) throws NoSuchHeaderException {
final Message in = exchange.getIn();
final Long jobId = BeanstalkExchangeHelper.getJobID(exchange);
final long priority = BeanstalkExchangeHelper.getPriority(endpoint, in);
final int delay = BeanstalkExchangeHelper.getDelay(endpoint, in);
final boolean result = client.release(jobId, priority, delay);
if (!result && LOG.isWarnEnabled()) {


log.info("failed to release job d priority d delay d");
}
}

};