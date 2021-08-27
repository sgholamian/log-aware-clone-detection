//,temp,CosmosDbProducer.java,322,337,temp,VertxKafkaProducerOperations.java,62,79
//,3
public class xxx {
    private <T> void subscribeToMono(
            final Mono<T> inputMono, final Exchange exchange, final Consumer<T> resultsCallback, final AsyncCallback callback) {
        inputMono
                .subscribe(resultsCallback, error -> {
                    // error but we continue
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Error processing async exchange with error: {}", error.getMessage());
                    }
                    exchange.setException(error);
                    callback.done(false);
                }, () -> {
                    // we are done from everything, so mark it as sync done
                    LOG.trace("All events with exchange have been sent successfully.");
                    callback.done(false);
                });
    }

};