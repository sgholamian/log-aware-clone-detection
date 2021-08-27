//,temp,ReleaseCommand.java,36,52,temp,PutCommand.java,36,50
//,3
public class xxx {
    @Override
    public void act(final Client client, final Exchange exchange) throws NoSuchHeaderException {
        final Message in = exchange.getIn();

        final long jobId = BeanstalkExchangeHelper.getJobID(exchange);
        final long priority = BeanstalkExchangeHelper.getPriority(endpoint, in);
        final int delay = BeanstalkExchangeHelper.getDelay(endpoint, in);

        final boolean result = client.release(jobId, priority, delay);
        if (!result) {
            LOG.warn("Failed to release job {} (priority {}, delay {})", jobId, priority, delay);
        } else {
            LOG.debug("Job {} released with priority {}, delay {} seconds. Result is {}", jobId, priority, delay, result);
        }

        answerWith(exchange, Headers.RESULT, result);
    }

};