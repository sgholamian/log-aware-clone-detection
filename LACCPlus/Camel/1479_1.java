//,temp,AggregateProcessor.java,1768,1790,temp,AggregateProcessor.java,1695,1721
//,3
public class xxx {
    public int forceDiscardingOfGroup(String key) {
        // must acquire the shared aggregation lock to be able to trigger force completion
        int total = 0;

        lock.lock();
        try {
            Exchange exchange = aggregationRepository.get(camelContext, key);
            if (exchange != null) {
                total = 1;
                LOG.trace("Force discarded triggered for correlation key: {}", key);
                // force discarding by setting aggregate failed as true
                onCompletion(key, exchange, exchange, false, true);
            }
        } finally {
            lock.unlock();
        }
        LOG.trace("Completed force discarded of group {}", key);

        if (total > 0) {
            LOG.debug("Forcing discarding of group {} with {} exchanges", key, total);
        }
        return total;
    }

};