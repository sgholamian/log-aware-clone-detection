//,temp,Athena2QueryHelper.java,276,293,temp,Athena2QueryHelper.java,254,271
//,2
public class xxx {
    private long determineDelay(final Exchange exchange, Athena2Configuration configuration) {
        Long delay = exchange.getIn().getHeader(Athena2Constants.DELAY, Long.class);

        if (ObjectHelper.isEmpty(delay)) {
            delay = configuration.getDelay();
            LOG.trace("AWS Athena delay is missing, using default one [{}]", delay);
        }

        if (ObjectHelper.isEmpty(delay)) {
            throw new IllegalArgumentException("AWS Athena delay is required.");
        }

        if (delay < 0) {
            throw new IllegalArgumentException("AWS Athena delay must be >= 0");
        }

        return delay;
    }

};