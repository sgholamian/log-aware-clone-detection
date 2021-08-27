//,temp,PrototypeExchangeFactory.java,138,169,temp,PrototypeProcessorExchangeFactory.java,122,147
//,3
public class xxx {
    void logUsageSummary(Logger log, String name, int pooled) {
        if (statisticsEnabled && processor != null) {
            // only log if there is any usage
            long created = statistics.getCreatedCounter();
            long acquired = statistics.getAcquiredCounter();
            long released = statistics.getReleasedCounter();
            long discarded = statistics.getDiscardedCounter();
            boolean shouldLog = pooled > 0 || created > 0 || acquired > 0 || released > 0 || discarded > 0;
            if (shouldLog) {
                String rid = getRouteId();
                String pid = getId();

                // are there any leaks?
                boolean leak = created + acquired > released + discarded;
                if (leak) {
                    long leaks = (created + acquired) - (released + discarded);
                    log.warn(
                            "{} {} ({}) usage (leaks detected: {}) [pooled: {}, created: {}, acquired: {} released: {}, discarded: {}]",
                            name, rid, pid, leaks, pooled, created, acquired, released, discarded);
                } else {
                    log.info("{} {} ({}) usage [pooled: {}, created: {}, acquired: {} released: {}, discarded: {}]",
                            name, rid, pid, pooled, created, acquired, released, discarded);
                }
            }
        }
    }

};