//,temp,StitchProducerOperations.java,57,74,temp,EventHubsProducerOperations.java,63,79
//,3
public class xxx {
    public boolean sendEvents(
            final Message inMessage, final Consumer<StitchResponse> resultCallback, final AsyncCallback callback) {
        sendAsyncEvents(inMessage)
                .subscribe(resultCallback, error -> {
                    // error but we continue
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Error processing async exchange with error: {}", error.getMessage());
                    }
                    inMessage.getExchange().setException(error);
                    callback.done(false);
                }, () -> {
                    // we are done from everything, so mark it as sync done
                    LOG.trace("All events with exchange have been sent successfully.");
                    callback.done(false);
                });

        return false;
    }

};