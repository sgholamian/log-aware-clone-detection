//,temp,Athena2Producer.java,144,153,temp,Athena2QueryHelper.java,383,398
//,3
public class xxx {
    private boolean determineResetWaitTimeoutOnRetry(final Exchange exchange, Athena2Configuration configuration) {
        Boolean resetWaitTimeoutOnRetry
                = exchange.getIn().getHeader(Athena2Constants.RESET_WAIT_TIMEOUT_ON_RETRY, Boolean.class);

        if (ObjectHelper.isEmpty(resetWaitTimeoutOnRetry)) {
            resetWaitTimeoutOnRetry = configuration.isResetWaitTimeoutOnRetry();
            LOG.trace("AWS Athena reset wait timeout on retry is missing, using default one [{}]",
                    resetWaitTimeoutOnRetry);
        }

        if (ObjectHelper.isEmpty(resetWaitTimeoutOnRetry)) {
            throw new IllegalArgumentException("AWS Athena reset wait timeout on retry is required.");
        }

        return resetWaitTimeoutOnRetry;
    }

};