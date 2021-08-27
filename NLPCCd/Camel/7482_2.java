//,temp,sample_2210.java,2,16,temp,sample_2209.java,2,15
//,3
public class xxx {
private <T> T doConvertTo(Class<T> type, Exchange exchange, Object value) throws Exception {
if (StreamCache.class.isAssignableFrom(value.getClass())) {
return null;
}
if (Future.class.isAssignableFrom(value.getClass())) {
Future<?> future = (Future<?>) value;
if (future.isCancelled()) {
return (T) Void.TYPE;
}


log.info("getting future response");
}
}

};