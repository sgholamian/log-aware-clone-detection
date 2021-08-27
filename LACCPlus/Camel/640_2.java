//,temp,DefaultJmsMessageListenerContainer.java,135,147,temp,DefaultJmsMessageListenerContainer.java,121,133
//,3
public class xxx {
    @Override
    public void stop() throws JmsException {
        if (logger.isDebugEnabled()) {
            logger.debug("Stopping listenerContainer: " + this + " with cacheLevel: " + getCacheLevel()
                         + " and sharedConnectionEnabled: " + sharedConnectionEnabled());
        }
        super.stop();

        if (taskExecutor instanceof ThreadPoolTaskExecutor) {
            ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) taskExecutor;
            executor.destroy();
        }
    }

};