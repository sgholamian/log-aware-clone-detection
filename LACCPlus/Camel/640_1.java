//,temp,DefaultJmsMessageListenerContainer.java,135,147,temp,DefaultJmsMessageListenerContainer.java,121,133
//,3
public class xxx {
    @Override
    public void destroy() {
        if (logger.isDebugEnabled()) {
            logger.debug("Destroying listenerContainer: " + this + " with cacheLevel: " + getCacheLevel()
                         + " and sharedConnectionEnabled: " + sharedConnectionEnabled());
        }
        super.destroy();

        if (taskExecutor instanceof ThreadPoolTaskExecutor) {
            ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) taskExecutor;
            executor.destroy();
        }
    }

};