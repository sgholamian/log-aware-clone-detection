//,temp,EhcacheAggregationRepository.java,148,158,temp,CaffeineAggregationRepository.java,128,138
//,2
public class xxx {
    @Override
    public Exchange add(final CamelContext camelContext, final String key, final Exchange exchange) {
        LOG.trace("Adding an Exchange with ID {} for key {} in a thread-safe manner.", exchange.getExchangeId(), key);

        final DefaultExchangeHolder oldHolder = cache.get(key);
        final DefaultExchangeHolder newHolder = DefaultExchangeHolder.marshal(exchange, true, allowSerializedHeaders);

        cache.put(key, newHolder);

        return unmarshallExchange(camelContext, oldHolder);
    }

};