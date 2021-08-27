//,temp,DefaultProducerCache.java,273,340,temp,DefaultProducerCache.java,164,206
//,3
public class xxx {
    @Override
    public boolean doInAsyncProducer(
            Endpoint endpoint,
            Exchange exchange,
            AsyncCallback callback,
            AsyncProducerCallback producerCallback) {

        AsyncProducer producer;
        try {
            // get the producer and we do not mind if its pooled as we can handle returning it back to the pool
            producer = acquireProducer(endpoint);

            if (producer == null) {
                if (isStopped()) {
                    LOG.warn("Ignoring exchange sent after processor is stopped: {}", exchange);
                    callback.done(true);
                    return true;
                } else {
                    exchange.setException(
                            new IllegalStateException("No producer, this processor has not been started: " + this));
                    callback.done(true);
                    return true;
                }
            }
        } catch (Throwable e) {
            exchange.setException(e);
            callback.done(true);
            return true;
        }

        try {
            // record timing for sending the exchange using the producer
            StopWatch watch;
            if (eventNotifierEnabled && camelContext.isEventNotificationApplicable()) {
                boolean sending = EventHelper.notifyExchangeSending(exchange.getContext(), exchange, endpoint);
                if (sending) {
                    watch = new StopWatch();
                } else {
                    watch = null;
                }
            } else {
                watch = null;
            }

            // invoke the callback
            return producerCallback.doInAsyncProducer(producer, exchange, doneSync -> {
                try {
                    if (watch != null) {
                        long timeTaken = watch.taken();
                        // emit event that the exchange was sent to the endpoint
                        EventHelper.notifyExchangeSent(exchange.getContext(), exchange, endpoint, timeTaken);
                    }

                    // release back to the pool
                    releaseProducer(endpoint, producer);
                } finally {
                    callback.done(doneSync);
                }
            });
        } catch (Throwable e) {
            // ensure exceptions is caught and set on the exchange
            if (exchange != null) {
                exchange.setException(e);
            }
            callback.done(true);
            return true;
        }
    }

};