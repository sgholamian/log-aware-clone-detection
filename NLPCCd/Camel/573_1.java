//,temp,sample_5327.java,2,14,temp,sample_5326.java,2,11
//,3
public class xxx {
protected synchronized void doStop() throws Exception {
if (cacheManager != null) {
int size = cacheManager.getCacheNames().length;
if (size <= 0) {
cacheManager.shutdown();
cacheManager = null;
} else {


log.info("cannot stop cachemanager as its still in use by clients");
}
}
}

};