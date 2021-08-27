//,temp,EhcacheAggregationRepository.java,185,191,temp,RedisAggregationRepository.java,142,155
//,3
public class xxx {
    @Override
    public Set<String> scan(CamelContext camelContext) {
        LOG.trace("Scanning for exchanges to recover in {} context", camelContext.getName());
        Set<String> scanned = Collections.unmodifiableSet(getKeys());
        LOG.trace("Found {} keys for exchanges to recover in {} context", scanned.size(), camelContext.getName());
        return scanned;
    }

};