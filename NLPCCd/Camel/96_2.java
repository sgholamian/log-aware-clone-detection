//,temp,sample_1261.java,2,12,temp,sample_1264.java,2,18
//,3
public class xxx {
public void dummy_method(){
DefaultExchangeHolder holder = DefaultExchangeHolder.marshal(exchange, true, allowSerializedHeaders);
if (optimistic) {
if (!cache.remove(key, holder)) {
throw new OptimisticLockingException();
}
if (useRecovery) {
persistedCache.put(exchange.getExchangeId(), holder);
}
} else {
if (useRecovery) {


log.info("removing an exchange with id for key in a thread safe manner");
}
}
}

};