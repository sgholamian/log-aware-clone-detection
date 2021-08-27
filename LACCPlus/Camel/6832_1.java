//,temp,GenericFileConsumer.java,214,259,temp,SplunkConsumer.java,105,125
//,3
public class xxx {
    @Override
    public int processBatch(Queue<Object> exchanges) {
        int total = exchanges.size();
        int answer = total;

        // limit if needed
        if (maxMessagesPerPoll > 0 && total > maxMessagesPerPoll) {
            LOG.debug("Limiting to maximum messages to poll {} as there were {} messages in this poll.", maxMessagesPerPoll,
                    total);
            total = maxMessagesPerPoll;
        }

        for (int index = 0; index < total && isBatchAllowed(); index++) {
            // only loop if we are started (allowed to run)
            // use poll to remove the head so it does not consume memory even
            // after we have processed it
            Exchange exchange = (Exchange) exchanges.poll();
            // add current index and total as properties
            exchange.setProperty(ExchangePropertyKey.BATCH_INDEX, index);
            exchange.setProperty(ExchangePropertyKey.BATCH_SIZE, total);
            exchange.setProperty(ExchangePropertyKey.BATCH_COMPLETE, index == total - 1);

            // update pending number of exchanges
            pendingExchanges = total - index - 1;

            // process the current exchange
            boolean started;
            if (customProcessor != null) {
                // use a custom processor
                started = customProcessExchange(exchange, customProcessor);
            } else {
                // process the exchange regular
                started = processExchange(exchange);
            }

            // if we did not start process the file then decrement the counter
            if (!started) {
                answer--;
            }
        }

        // drain any in progress files as we are done with this batch
        removeExcessiveInProgressFiles(CastUtils.cast((Deque<?>) exchanges, Exchange.class), 0);

        return answer;
    }

};