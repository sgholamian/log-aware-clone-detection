//,temp,sample_2729.java,2,11,temp,sample_2730.java,2,12
//,3
public class xxx {
public ExecutorService newCachedThreadPool(Object source, String name) {
String sanitizedName = URISupport.sanitizeUri(name);
ExecutorService answer = threadPoolFactory.newCachedThreadPool(createThreadFactory(sanitizedName, true));
onThreadPoolCreated(answer, source, null);
if (LOG.isDebugEnabled()) {


log.info("created new cachedthreadpool for source with name");
}
}

};