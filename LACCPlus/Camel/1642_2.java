//,temp,DefaultProducerCache.java,273,340,temp,DefaultProducerCache.java,164,206
//,3
public class xxx {
    @Override
    public Exchange send(Endpoint endpoint, Exchange exchange, Processor resultProcessor) {
        if (camelContext.isStopped()) {
            exchange.setException(new RejectedExecutionException("CamelContext is stopped"));
            return exchange;
        }

        AsyncProducer producer = acquireProducer(endpoint);
        try {
            // now lets dispatch
            LOG.debug(">>>> {} {}", endpoint, exchange);

            // set property which endpoint we send to
            exchange.setProperty(ExchangePropertyKey.TO_ENDPOINT, endpoint.getEndpointUri());

            // send the exchange using the processor
            StopWatch watch = null;
            try {
                if (eventNotifierEnabled && camelContext.isEventNotificationApplicable()) {
                    boolean sending = EventHelper.notifyExchangeSending(exchange.getContext(), exchange, endpoint);
                    if (sending) {
                        watch = new StopWatch();
                    }
                }

                // invoke the synchronous method
                sharedInternalProcessor.process(exchange, producer, resultProcessor);

            } catch (Throwable e) {
                // ensure exceptions is caught and set on the exchange
                exchange.setException(e);
            } finally {
                // emit event that the exchange was sent to the endpoint
                if (watch != null) {
                    long timeTaken = watch.taken();
                    EventHelper.notifyExchangeSent(exchange.getContext(), exchange, endpoint, timeTaken);
                }
            }
            return exchange;
        } finally {
            releaseProducer(endpoint, producer);
        }
    }

};