//,temp,sample_6564.java,2,14,temp,sample_6827.java,2,13
//,3
public class xxx {
public void act(final Client client, final Exchange exchange) throws NoSuchHeaderException {
final Message in = exchange.getIn();
final long priority = BeanstalkExchangeHelper.getPriority(endpoint, in);
final int delay = BeanstalkExchangeHelper.getDelay(endpoint, in);
final int timeToRun = BeanstalkExchangeHelper.getTimeToRun(endpoint, in);
final long jobId = client.put(priority, delay, timeToRun, in.getBody(byte[].class));
if (LOG.isDebugEnabled()) {


log.info("created job d with priority d delay d seconds and time to run d");
}
}

};