//,temp,AggregateProcessor.java,1792,1831,temp,AggregateProcessor.java,1723,1766
//,3
public class xxx {
    public int forceDiscardingOfAllGroups() {

        // only run if CamelContext has been fully started or is stopping
        boolean allow = camelContext.getStatus().isStarted() || camelContext.getStatus().isStopping();
        if (!allow) {
            LOG.warn("Cannot start force discarding of all groups because CamelContext({}) has not been started",
                    camelContext.getName());
            return 0;
        }

        LOG.trace("Starting force discarding of all groups task");

        // trigger completion for all in the repository
        Set<String> keys = aggregationRepository.getKeys();

        int total = 0;
        if (keys != null && !keys.isEmpty()) {
            // must acquire the shared aggregation lock to be able to trigger force completion
            lock.lock();
            total = keys.size();
            try {
                for (String key : keys) {
                    Exchange exchange = aggregationRepository.get(camelContext, key);
                    if (exchange != null) {
                        LOG.trace("Force discarded triggered for correlation key: {}", key);
                        // force discarding by setting aggregate failed as true
                        onCompletion(key, exchange, exchange, false, true);
                    }
                }
            } finally {
                lock.unlock();
            }
        }
        LOG.trace("Completed force discarding of all groups task");

        if (total > 0) {
            LOG.debug("Forcing discarding of all groups with {} exchanges", total);
        }
        return total;
    }

};