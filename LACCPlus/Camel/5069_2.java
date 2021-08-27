//,temp,ReplicatedHazelcastAggregationRepository.java,155,184,temp,JCacheAggregationRepository.java,93,125
//,3
public class xxx {
    @Override
    public Exchange add(CamelContext camelContext, String key, Exchange oldExchange, Exchange newExchange)
            throws OptimisticLockingException {
        if (!optimistic) {
            throw new UnsupportedOperationException();
        }

        LOG.trace("Adding an Exchange with ID {} for key {} in an optimistic manner.", newExchange.getExchangeId(), key);
        if (oldExchange == null) {
            DefaultExchangeHolder newHolder = DefaultExchangeHolder.marshal(newExchange, true, allowSerializedHeaders);
            DefaultExchangeHolder oldHolder = cache.getAndPut(key, newHolder);
            if (oldHolder != null) {
                Exchange exchange = unmarshallExchange(camelContext, oldHolder);
                LOG.error(
                        "Optimistic locking failed for exchange with key {}: IMap#putIfAbsend returned Exchange with ID {}, while it's expected no exchanges to be returned",
                        key,
                        exchange != null ? exchange.getExchangeId() : "<null>");

                throw new OptimisticLockingException();
            }
        } else {
            DefaultExchangeHolder oldHolder = DefaultExchangeHolder.marshal(oldExchange, true, allowSerializedHeaders);
            DefaultExchangeHolder newHolder = DefaultExchangeHolder.marshal(newExchange, true, allowSerializedHeaders);
            if (!cache.replace(key, oldHolder, newHolder)) {
                LOG.error(
                        "Optimistic locking failed for exchange with key {}: IMap#replace returned no Exchanges, while it's expected to replace one",
                        key);
                throw new OptimisticLockingException();
            }
        }
        LOG.trace("Added an Exchange with ID {} for key {} in optimistic manner.", newExchange.getExchangeId(), key);
        return oldExchange;
    }

};