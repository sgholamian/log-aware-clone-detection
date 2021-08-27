//,temp,DataLakeConsumer.java,128,158,temp,BlobConsumer.java,125,159
//,2
public class xxx {
    @Override
    public int processBatch(Queue<Object> exchanges) {
        final int total = exchanges.size();

        for (int i = 0; i < total && isBatchAllowed(); i++) {
            final Exchange exchange = ObjectHelper.cast(Exchange.class, exchanges.poll());

            exchange.setProperty(ExchangePropertyKey.BATCH_INDEX, i);
            exchange.setProperty(ExchangePropertyKey.BATCH_SIZE, total);
            exchange.setProperty(ExchangePropertyKey.BATCH_COMPLETE, i == total - 1);

            pendingExchanges = total - i - 1;

            exchange.adapt(ExtendedExchange.class).addOnCompletion(new Synchronization() {
                @Override
                public void onComplete(Exchange exchange) {
                    LOG.trace("Processing all exchanges completed");
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