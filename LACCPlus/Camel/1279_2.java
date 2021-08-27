//,temp,BaseExecutorServiceManager.java,246,258,temp,BaseExecutorServiceManager.java,185,201
//,3
public class xxx {
    @Override
    public ExecutorService newThreadPool(Object source, String name, ThreadPoolProfile profile) {
        String sanitizedName = URISupport.sanitizeUri(name);
        ObjectHelper.notNull(profile, "ThreadPoolProfile");

        ThreadPoolProfile defaultProfile = getDefaultThreadPoolProfile();
        profile.addDefaults(defaultProfile);

        ThreadFactory threadFactory = createThreadFactory(sanitizedName, true);
        ExecutorService executorService = threadPoolFactory.newThreadPool(profile, threadFactory);
        onThreadPoolCreated(executorService, source, profile.getId());
        if (LOG.isDebugEnabled()) {
            LOG.debug("Created new ThreadPool for source: {} with name: {}. -> {}", source, sanitizedName, executorService);
        }

        return executorService;
    }

};