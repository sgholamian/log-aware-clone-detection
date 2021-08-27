//,temp,Athena2QueryHelper.java,276,293,temp,Athena2QueryHelper.java,254,271
//,2
public class xxx {
    private long determineWaitTimeout(final Exchange exchange, Athena2Configuration configuration) {
        Long waitTimeout = exchange.getIn().getHeader(Athena2Constants.WAIT_TIMEOUT, Long.class);

        if (ObjectHelper.isEmpty(waitTimeout)) {
            waitTimeout = configuration.getWaitTimeout();
            LOG.trace("AWS Athena wait timeout is missing, using default one [{}]", waitTimeout);
        }

        if (ObjectHelper.isEmpty(waitTimeout)) {
            throw new IllegalArgumentException("AWS Athena wait timeout required.");
        }

        if (waitTimeout < 0) {
            throw new IllegalArgumentException("AWS Athena wait timeout must be >= 0");
        }

        return waitTimeout;
    }

};