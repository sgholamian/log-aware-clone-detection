//,temp,AggregateProcessor.java,1768,1790,temp,AggregateProcessor.java,1695,1721
//,3
public class xxx {
    public int forceCompletionOfGroup(String key) {
        // must acquire the shared aggregation lock to be able to trigger force completion
        int total = 0;

        lock.lock();
        try {
            Exchange exchange = aggregationRepository.get(camelContext, key);
            if (exchange != null) {
                total = 1;
                LOG.trace("Force completion triggered for correlation key: {}", key);
                // indicate it was completed by a force completion request
                exchange.setProperty(ExchangePropertyKey.AGGREGATED_COMPLETED_BY, COMPLETED_BY_FORCE);
                Exchange answer = onCompletion(key, exchange, exchange, false, false);
                if (answer != null) {
                    onSubmitCompletion(key, answer);
                }
            }
        } finally {
            lock.unlock();
        }
        LOG.trace("Completed force completion of group {}", key);

        if (total > 0) {
            LOG.debug("Forcing completion of group {} with {} exchanges", key, total);
        }
        return total;
    }

};