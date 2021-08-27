//,temp,HazelcastAggregationRepository.java,219,235,temp,RedisAggregationRepository.java,124,140
//,3
public class xxx {
    @Override
    public Exchange add(CamelContext camelContext, String key, Exchange exchange) {
        if (optimistic) {
            throw new UnsupportedOperationException();
        }
        LOG.trace("Adding an Exchange with ID {} for key {} in a thread-safe manner.", exchange.getExchangeId(), key);
        RLock lock = redisson.getLock("aggregationLock");
        try {
            lock.lock();
            DefaultExchangeHolder newHolder = DefaultExchangeHolder.marshal(exchange, true, allowSerializedHeaders);
            DefaultExchangeHolder oldHolder = cache.put(key, newHolder);
            return unmarshallExchange(camelContext, oldHolder);
        } finally {
            LOG.trace("Added an Exchange with ID {} for key {} in a thread-safe manner.", exchange.getExchangeId(), key);
            lock.unlock();
        }
    }

};