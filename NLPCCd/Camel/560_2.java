//,temp,sample_8211.java,2,11,temp,sample_1016.java,2,12
//,3
public class xxx {
public void act(final Client client, final Exchange exchange) throws NoSuchHeaderException {
final Long jobId = BeanstalkExchangeHelper.getJobID(exchange);
final long priority = BeanstalkExchangeHelper.getPriority(endpoint, exchange.getIn());
final boolean result = client.bury(jobId, priority);
if (!result && LOG.isWarnEnabled()) {
} else if (LOG.isDebugEnabled()) {


log.info("job d buried with priority d result is b");
}
}

};