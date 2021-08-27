//,temp,DeleteCommand.java,35,46,temp,TouchCommand.java,35,46
//,3
public class xxx {
    @Override
    public void act(final Client client, final Exchange exchange) throws NoSuchHeaderException {
        final Long jobId = ExchangeHelper.getMandatoryHeader(exchange, Headers.JOB_ID, Long.class);
        final boolean result = client.touch(jobId);
        if (!result) {
            LOG.warn("Failed to touch job {}", jobId);
        } else {
            LOG.debug("Job {} touched. Result is {}", jobId, result);
        }

        answerWith(exchange, Headers.RESULT, result);
    }

};