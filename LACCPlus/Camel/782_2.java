//,temp,EhcacheAggregationRepository.java,185,191,temp,RedisAggregationRepository.java,142,155
//,3
public class xxx {
    @Override
    public Set<String> scan(CamelContext camelContext) {
        if (useRecovery) {
            LOG.trace("Scanning for exchanges to recover in {} context", camelContext.getName());
            Set<String> scanned = Collections.unmodifiableSet(persistedCache.keySet());
            LOG.trace("Found {} keys for exchanges to recover in {} context", scanned.size(), camelContext.getName());
            return scanned;
        } else {
            LOG.warn(
                    "What for to run recovery scans in {} context while repository {} is running in non-recoverable aggregation repository mode?!",
                    camelContext.getName(), mapName);
            return Collections.emptySet();
        }
    }

};