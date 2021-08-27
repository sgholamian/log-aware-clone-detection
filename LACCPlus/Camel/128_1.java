//,temp,EhcacheAggregationRepository.java,193,197,temp,HazelcastAggregationRepository.java,252,256
//,2
public class xxx {
    @Override
    public Exchange recover(CamelContext camelContext, String exchangeId) {
        LOG.trace("Recovering an Exchange with ID {}.", exchangeId);
        return useRecovery ? unmarshallExchange(camelContext, cache.get(exchangeId)) : null;
    }

};