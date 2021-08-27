//,temp,HazelcastAggregationRepository.java,338,397,temp,RedisAggregationRepository.java,274,326
//,3
public class xxx {
    @Override
    public void remove(CamelContext camelContext, String key, Exchange exchange) {
        DefaultExchangeHolder holder = DefaultExchangeHolder.marshal(exchange, true, allowSerializedHeaders);
        if (optimistic) {
            LOG.trace("Removing an exchange with ID {} for key {} in an optimistic manner.", exchange.getExchangeId(), key);
            if (!cache.remove(key, holder)) {
                LOG.error(
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
                // The only considerable case for transaction usage is fault tolerance:
                // the transaction will be rolled back automatically (default timeout is 2 minutes)
                // if no commit occurs during the timeout. So we are still consistent whether local node crashes.
                TransactionOptions tOpts = new TransactionOptions();

                tOpts.setTransactionType(TransactionOptions.TransactionType.ONE_PHASE);
                TransactionContext tCtx = hzInstance.newTransactionContext(tOpts);

                try {
                    tCtx.beginTransaction();

                    TransactionalMap<String, DefaultExchangeHolder> tCache = tCtx.getMap(cache.getName());
                    TransactionalMap<String, DefaultExchangeHolder> tPersistentCache = tCtx.getMap(persistedCache.getName());

                    DefaultExchangeHolder removedHolder = tCache.remove(key);
                    LOG.trace("Putting an exchange with ID {} for key {} into a recoverable storage in a thread-safe manner.",
                            exchange.getExchangeId(), key);
                    tPersistentCache.put(exchange.getExchangeId(), removedHolder);

                    tCtx.commitTransaction();
                    LOG.trace("Removed an exchange with ID {} for key {} in a thread-safe manner.", exchange.getExchangeId(),
                            key);
                    LOG.trace("Put an exchange with ID {} for key {} into a recoverable storage in a thread-safe manner.",
                            exchange.getExchangeId(), key);
                } catch (Throwable throwable) {
                    tCtx.rollbackTransaction();

                    final String msg = String.format(
                            "Transaction with ID %s was rolled back for remove operation with a key %s and an Exchange ID %s.",
                            tCtx.getTxnId(), key, exchange.getExchangeId());
                    LOG.warn(msg, throwable);
                    throw new RuntimeException(msg, throwable);
                }
            } else {
                cache.remove(key);
            }
        }
    }

};