//,temp,DeleteCommand.java,35,46,temp,TouchCommand.java,35,46
//,3
public class xxx {
    @Override
    public void act(final Client client, final Exchange exchange) throws NoSuchHeaderException {
        final long jobId = BeanstalkExchangeHelper.getJobID(exchange);
        final boolean result = client.delete(jobId);
        if (!result) {
            LOG.warn("Failed to delete job {}", jobId);
        } else {
            LOG.debug("Job {} deleted. Result is {}", jobId, result);
        }

        answerWith(exchange, Headers.RESULT, result);
    }

};