//,temp,HazelcastAggregationRepository.java,188,217,temp,RedisAggregationRepository.java,93,122
//,2
public class xxx {
    @Override
    public Exchange add(CamelContext camelContext, String key, Exchange oldExchange, Exchange newExchange)
            throws OptimisticLockingException {
        if (!optimistic) {
            throw new UnsupportedOperationException();
        }
        LOG.trace("Adding an Exchange with ID {} for key {} in an optimistic manner.", newExchange.getExchangeId(), key);
        if (oldExchange == null) {
            DefaultExchangeHolder holder = DefaultExchangeHolder.marshal(newExchange, true, allowSerializedHeaders);
            final DefaultExchangeHolder misbehaviorHolder = cache.putIfAbsent(key, holder);
            if (misbehaviorHolder != null) {
                Exchange misbehaviorEx = unmarshallExchange(camelContext, misbehaviorHolder);
                LOG.warn(
                        "Optimistic locking failed for exchange with key {}: IMap#putIfAbsend returned Exchange with ID {}, while it's expected no exchanges to be returned",
                        key, misbehaviorEx != null ? misbehaviorEx.getExchangeId() : "<null>");
                throw new OptimisticLockingException();
            }
        } else {
            DefaultExchangeHolder oldHolder = DefaultExchangeHolder.marshal(oldExchange, true, allowSerializedHeaders);
            DefaultExchangeHolder newHolder = DefaultExchangeHolder.marshal(newExchange, true, allowSerializedHeaders);
            if (!cache.replace(key, oldHolder, newHolder)) {
                LOG.warn(
                        "Optimistic locking failed for exchange with key {}: IMap#replace returned no Exchanges, while it's expected to replace one",
                        key);
                throw new OptimisticLockingException();
            }
        }
        LOG.trace("Added an Exchange with ID {} for key {} in optimistic manner.", newExchange.getExchangeId(), key);
        return oldExchange;
    }

};