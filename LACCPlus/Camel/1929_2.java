//,temp,HBaseConsumer.java,157,192,temp,MyBatisConsumer.java,105,158
//,3
public class xxx {
    @Override
    public int processBatch(Queue<Object> exchanges) throws Exception {
        final MyBatisEndpoint endpoint = getEndpoint();

        int total = exchanges.size();

        // limit if needed
        if (maxMessagesPerPoll > 0 && total > maxMessagesPerPoll) {
            LOG.debug("Limiting to maximum messages to poll {} as there were {} messages in this poll.",
                    maxMessagesPerPoll, total);
            total = maxMessagesPerPoll;
        }

        for (int index = 0; index < total && isBatchAllowed(); index++) {
            // only loop if we are started (allowed to run)
            DataHolder holder = ObjectHelper.cast(DataHolder.class, exchanges.poll());
            Exchange exchange = holder.exchange;
            Object data = holder.data;

            // add current index and total as properties
            exchange.setProperty(ExchangePropertyKey.BATCH_INDEX, index);
            exchange.setProperty(ExchangePropertyKey.BATCH_SIZE, total);
            exchange.setProperty(ExchangePropertyKey.BATCH_COMPLETE, index == total - 1);

            // update pending number of exchanges
            pendingExchanges = total - index - 1;

            Exception cause = null;
            try {
                getProcessor().process(exchange);

                if (onConsume != null) {
                    endpoint.getProcessingStrategy().commit(endpoint, exchange, data, onConsume);
                }
            } catch (Exception e) {
                handleException(e);
            }

            if (getEndpoint().isTransacted() && exchange.isFailed()) {
                // break out as we are transacted and should rollback
                cause = exchange.getException();
                if (cause == null) {
                    cause = new RollbackExchangeException("Rollback transaction due error processing exchange", null);
                }
            }
            releaseExchange(exchange, false);

            if (cause != null) {
                throw cause;
            }
        }

        return total;
    }

};