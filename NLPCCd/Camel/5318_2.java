//,temp,sample_2729.java,2,11,temp,sample_2730.java,2,12
//,3
public class xxx {
public ScheduledExecutorService newScheduledThreadPool(Object source, String name, ThreadPoolProfile profile) {
String sanitizedName = URISupport.sanitizeUri(name);
profile.addDefaults(getDefaultThreadPoolProfile());
ScheduledExecutorService answer = threadPoolFactory.newScheduledThreadPool(profile, createThreadFactory(sanitizedName, true));
onThreadPoolCreated(answer, source, null);
if (LOG.isDebugEnabled()) {


log.info("created new scheduledthreadpool for source with name");
}
}

};