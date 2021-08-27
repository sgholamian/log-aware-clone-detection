//,temp,StitchProducerOperations.java,57,74,temp,EventHubsProducerOperations.java,63,79
//,3
public class xxx {
    private boolean sendAsyncEvents(
            final Iterable<EventData> eventData, final SendOptions sendOptions, final Exchange exchange,
            final AsyncCallback asyncCallback) {
        sendAsyncEventsWithSuitableMethod(eventData, sendOptions)
                .subscribe(unused -> LOG.debug("Processed one event..."), error -> {
                    // error but we continue
                    LOG.debug("Error processing async exchange with error:" + error.getMessage());
                    exchange.setException(error);
                    asyncCallback.done(false);
                }, () -> {
                    // we are done from everything, so mark it as sync done
                    LOG.debug("All events with exchange have been sent successfully.");
                    asyncCallback.done(false);
                });

        return false;
    }

};