//,temp,DataLakeConsumer.java,128,158,temp,BlobConsumer.java,125,159
//,2
public class xxx {
    @Override
    public int processBatch(Queue<Object> exchanges) {
        final int total = exchanges.size();

        for (int index = 0; index < total && isBatchAllowed(); index++) {
            // only loop if we are started (allowed to run)
            final Exchange exchange = ObjectHelper.cast(Exchange.class, exchanges.poll());

            // add current index and total as properties
            exchange.setProperty(ExchangePropertyKey.BATCH_INDEX, index);
            exchange.setProperty(ExchangePropertyKey.BATCH_SIZE, total);
            exchange.setProperty(ExchangePropertyKey.BATCH_COMPLETE, index == total - 1);

            // update pending number of exchanges
            pendingExchanges = total - index - 1;

            // add on completion to handle after work when the exchange is done
            exchange.adapt(ExtendedExchange.class).addOnCompletion(new Synchronization() {
                @Override
                public void onComplete(Exchange exchange) {
                    LOG.trace("Complected from processing all exchanges...");
                }

                @Override
                public void onFailure(Exchange exchange) {
                    processRollback(exchange);
                }
            });

            // use default consumer callback
            AsyncCallback cb = defaultConsumerCallback(exchange, true);
            getAsyncProcessor().process(exchange, cb);
        }
        return total;
    }

};