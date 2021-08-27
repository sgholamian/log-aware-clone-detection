//,temp,EhcacheAggregationRepository.java,165,169,temp,InfinispanAggregationRepository.java,82,86
//,3
public class xxx {
    @Override
    public void remove(CamelContext camelContext, String key, Exchange exchange) {
        LOG.trace("Removing an exchange with ID {} for key {}", exchange.getExchangeId(), key);
        getCache().remove(key);
    }

};