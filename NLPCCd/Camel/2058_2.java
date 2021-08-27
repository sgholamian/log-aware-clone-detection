//,temp,sample_5757.java,2,14,temp,sample_5756.java,2,13
//,2
public class xxx {
public void start() throws Exception {
if (cacheExpression == null && cachePredicate == null && getCamelContext() != null) {
int maxSize = CamelContextHelper.getMaximumSimpleCacheSize(getCamelContext());
if (maxSize > 0) {
cacheExpression = LRUCacheFactory.newLRUCache(16, maxSize, false);
cachePredicate = LRUCacheFactory.newLRUCache(16, maxSize, false);


log.info("simple language predicate expression cache size");
}
}
}

};