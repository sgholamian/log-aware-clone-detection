//,temp,InfinispanAggregationRepository.java,107,111,temp,RedisAggregationRepository.java,157,161
//,3
public class xxx {
    @Override
    public Exchange recover(CamelContext camelContext, String exchangeId) {
        LOG.trace("Recovering an Exchange with ID {}.", exchangeId);
        return useRecovery ? unmarshallExchange(camelContext, getCache().get(exchangeId)) : null;
    }

};