//,temp,sample_1263.java,2,15,temp,sample_7540.java,2,12
//,3
public class xxx {
public void remove(CamelContext camelContext, String key, Exchange exchange) {
DefaultExchangeHolder holder = DefaultExchangeHolder.marshal(exchange, true, allowSerializedHeaders);
if (optimistic) {
if (!cache.remove(key, holder)) {
throw new OptimisticLockingException();
}
if (useRecovery) {
persistedCache.put(exchange.getExchangeId(), holder);


log.info("put an exchange with id for key into a recoverable storage in an optimistic manner");
}
}
}

};