//,temp,sample_55.java,2,15,temp,sample_56.java,2,16
//,3
public class xxx {
private HiveClientCache(final int timeout, final int initialCapacity, final int maxCapacity, final boolean enableStats) {
this.timeout = timeout;
this.enableStats = enableStats;
LOG.info("Initializing cache: eviction-timeout=" + timeout + " initial-capacity=" + initialCapacity + " maximum-capacity=" + maxCapacity);
CacheBuilder builder = CacheBuilder.newBuilder() .initialCapacity(initialCapacity) .maximumSize(maxCapacity) .expireAfterAccess(timeout, TimeUnit.SECONDS) .removalListener(createRemovalListener());
try {
java.lang.reflect.Method m = builder.getClass().getMethod("recordStats", null);
m.invoke(builder, null);
} catch (NoSuchMethodException e) {


log.info("using a version of guava stats collection is enabled by default");
}
}

};