//,temp,JCacheAggregationRepository.java,127,136,temp,InfinispanAggregationRepository.java,69,75
//,3
public class xxx {
    @Override
    public Exchange add(final CamelContext camelContext, final String key, final Exchange exchange) {
        LOG.trace("Adding an Exchange with ID {} for key {} in a thread-safe manner.", exchange.getExchangeId(), key);
        DefaultExchangeHolder newHolder = DefaultExchangeHolder.marshal(exchange, true, allowSerializedHeaders);
        DefaultExchangeHolder oldHolder = getCache().put(key, newHolder);
        return unmarshallExchange(camelContext, oldHolder);
    }

};