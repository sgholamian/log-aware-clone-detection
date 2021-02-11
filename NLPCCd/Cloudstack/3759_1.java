//,temp,sample_557.java,2,15,temp,sample_6152.java,2,15
//,2
public class xxx {
protected void createCache(final Map<String, ? extends Object> params) {
final String value = (String)params.get("cache.size");
if (value != null) {
final CacheManager cm = CacheManager.create();
final int maxElements = NumbersUtil.parseInt(value, 0);
final int live = NumbersUtil.parseInt((String)params.get("cache.time.to.live"), 300);
final int idle = NumbersUtil.parseInt((String)params.get("cache.time.to.idle"), 300);
_cache = new Cache(getName(), maxElements, false, live == -1, live == -1 ? Integer.MAX_VALUE : live, idle);
cm.addCache(_cache);


log.info("cache created");
}
}

};