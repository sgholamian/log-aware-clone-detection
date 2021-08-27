//,temp,BaseExecutorServiceManager.java,246,258,temp,BaseExecutorServiceManager.java,185,201
//,3
public class xxx {
    @Override
    public ScheduledExecutorService newScheduledThreadPool(Object source, String name, ThreadPoolProfile profile) {
        String sanitizedName = URISupport.sanitizeUri(name);
        profile.addDefaults(getDefaultThreadPoolProfile());
        ScheduledExecutorService answer
                = threadPoolFactory.newScheduledThreadPool(profile, createThreadFactory(sanitizedName, true));
        onThreadPoolCreated(answer, source, null);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Created new ScheduledThreadPool for source: {} with name: {} -> {}", source, sanitizedName, answer);
        }
        return answer;
    }

};