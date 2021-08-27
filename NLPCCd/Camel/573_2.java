//,temp,sample_5327.java,2,14,temp,sample_5326.java,2,11
//,3
public class xxx {
protected synchronized void doStop() throws Exception {
if (cacheManager != null) {
int size = cacheManager.getCacheNames().length;
if (size <= 0) {


log.info("shutting down cachemanager as its no longer in use");
}
}
}

};