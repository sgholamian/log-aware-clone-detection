//,temp,sample_6563.java,2,13,temp,sample_1015.java,2,11
//,3
public class xxx {
public void act(final Client client, final Exchange exchange) throws NoSuchHeaderException {
final Long jobId = BeanstalkExchangeHelper.getJobID(exchange);
final long priority = BeanstalkExchangeHelper.getPriority(endpoint, exchange.getIn());
final boolean result = client.bury(jobId, priority);
if (!result && LOG.isWarnEnabled()) {


log.info("failed to bury job d with priority d");
}
}

};