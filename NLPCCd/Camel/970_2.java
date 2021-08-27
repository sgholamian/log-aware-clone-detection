//,temp,sample_1119.java,2,11,temp,sample_1118.java,2,10
//,3
public class xxx {
public void act(final Client client, final Exchange exchange) throws NoSuchHeaderException {
final Long jobId = ExchangeHelper.getMandatoryHeader(exchange, Headers.JOB_ID, Long.class);
final boolean result = client.touch(jobId);
if (!result && LOG.isWarnEnabled()) {


log.info("failed to touch job d");
}
}

};