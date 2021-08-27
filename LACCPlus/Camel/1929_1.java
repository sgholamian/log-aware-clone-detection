//,temp,HBaseConsumer.java,157,192,temp,MyBatisConsumer.java,105,158
//,3
public class xxx {
    @Override
    public int processBatch(Queue<Object> exchanges) throws Exception {
        int total = exchanges.size();

        // limit if needed
        if (maxMessagesPerPoll > 0 && total > maxMessagesPerPoll) {
            LOG.debug("Limiting to maximum messages to poll {} as there were {} messages in this poll.", maxMessagesPerPoll,
                    total);
            total = maxMessagesPerPoll;
        }

        for (int index = 0; index < total && isBatchAllowed(); index++) {
            // only loop if we are started (allowed to run)
            Exchange exchange = ObjectHelper.cast(Exchange.class, exchanges.poll());
            // add current index and total as properties
            exchange.setProperty(ExchangePropertyKey.BATCH_INDEX, index);
            exchange.setProperty(ExchangePropertyKey.BATCH_SIZE, total);
            exchange.setProperty(ExchangePropertyKey.BATCH_COMPLETE, index == total - 1);

            // update pending number of exchanges
            pendingExchanges = total - index - 1;

            LOG.trace("Processing exchange [{}]...", exchange);
            getProcessor().process(exchange);
            if (exchange.getException() != null) {
                // if we failed then throw exception
                throw exchange.getException();
            }

            if (endpoint.isRemove()) {
                remove((byte[]) exchange.getIn().getHeader(HBaseAttribute.HBASE_MARKED_ROW_ID.asHeader()));
            }
        }

        return total;
    }

};