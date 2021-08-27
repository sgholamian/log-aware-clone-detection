//,temp,HazelcastAggregationRepository.java,338,397,temp,RedisAggregationRepository.java,274,326
//,3
public class xxx {
    @Override
    public void remove(CamelContext camelContext, String key, Exchange exchange) {
        DefaultExchangeHolder holder = DefaultExchangeHolder.marshal(exchange, true, allowSerializedHeaders);
        if (optimistic) {
            LOG.trace("Removing an exchange with ID {} for key {} in an optimistic manner.", exchange.getExchangeId(), key);
            if (!cache.remove(key, holder)) {
                LOG.warn(
                        "Optimistic locking failed for exchange with key {}: IMap#remove removed no Exchanges, while it's expected to remove one.",
                        key);
                throw new OptimisticLockingException();
            }
            LOG.trace("Removed an exchange with ID {} for key {} in an optimistic manner.", exchange.getExchangeId(), key);
            if (useRecovery) {
                LOG.trace("Putting an exchange with ID {} for key {} into a recoverable storage in an optimistic manner.",
                        exchange.getExchangeId(), key);
                persistedCache.put(exchange.getExchangeId(), holder);
                LOG.trace("Put an exchange with ID {} for key {} into a recoverable storage in an optimistic manner.",
                        exchange.getExchangeId(), key);
            }
        } else {
            if (useRecovery) {
                LOG.trace("Removing an exchange with ID {} for key {} in a thread-safe manner.", exchange.getExchangeId(), key);
                TransactionOptions tOpts = TransactionOptions.defaults();
                RTransaction transaction = redisson.createTransaction(tOpts);

                try {
                    RMap<String, DefaultExchangeHolder> tCache = transaction.getMap(mapName);
                    RMap<String, DefaultExchangeHolder> tPersistentCache = transaction.getMap(persistenceMapName);

                    DefaultExchangeHolder removedHolder = tCache.remove(key);
                    LOG.trace("Putting an exchange with ID {} for key {} into a recoverable storage in a thread-safe manner.",
                            exchange.getExchangeId(), key);
                    tPersistentCache.put(exchange.getExchangeId(), removedHolder);

                    transaction.commit();
                    LOG.trace("Removed an exchange with ID {} for key {} in a thread-safe manner.", exchange.getExchangeId(),
                            key);
                    LOG.trace("Put an exchange with ID {} for key {} into a recoverable storage in a thread-safe manner.",
                            exchange.getExchangeId(), key);
                } catch (Exception throwable) {
                    transaction.rollback();

                    final String msg = String.format(
                            "Transaction was rolled back for remove operation with a key %s and an Exchange ID %s.",
                            key, exchange.getExchangeId());
                    LOG.warn(msg, throwable);
                    throw new RuntimeException(msg, throwable);
                }
            } else {
                cache.remove(key);
            }
        }
    }

};