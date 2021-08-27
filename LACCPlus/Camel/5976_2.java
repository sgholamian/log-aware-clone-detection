//,temp,ReleaseCommand.java,36,52,temp,PutCommand.java,36,50
//,3
public class xxx {
    @Override
    public void act(final Client client, final Exchange exchange) throws NoSuchHeaderException {
        final Message in = exchange.getIn();

        final long priority = BeanstalkExchangeHelper.getPriority(endpoint, in);
        final int delay = BeanstalkExchangeHelper.getDelay(endpoint, in);
        final int timeToRun = BeanstalkExchangeHelper.getTimeToRun(endpoint, in);

        final long jobId = client.put(priority, delay, timeToRun, in.getBody(byte[].class));

        LOG.debug("Created job {} with priority {}, delay {} seconds and time to run {}", jobId, priority, delay,
                timeToRun);

        answerWith(exchange, Headers.JOB_ID, jobId);
    }

};