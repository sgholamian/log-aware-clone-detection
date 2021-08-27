//,temp,CaffeineAggregationRepository.java,164,170,temp,InfinispanAggregationRepository.java,99,105
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