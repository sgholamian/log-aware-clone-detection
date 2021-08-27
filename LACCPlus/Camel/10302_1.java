//,temp,ReplicatedHazelcastAggregationRepository.java,219,223,temp,CaffeineAggregationRepository.java,172,176
//,2
public class xxx {
    @Override
    public Exchange recover(CamelContext camelContext, String exchangeId) {
        LOG.trace("Recovering an Exchange with ID {}.", exchangeId);
        return useRecovery ? unmarshallExchange(camelContext, replicatedPersistedCache.get(exchangeId)) : null;
    }

};