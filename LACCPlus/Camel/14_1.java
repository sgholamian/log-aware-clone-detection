//,temp,DefaultProducer.java,76,84,temp,DefaultProducer.java,66,74
//,2
public class xxx {
    @Override
    protected void doStop() throws Exception {
        // log at debug level for singletons, for prototype scoped log at trace level to not spam logs
        if (isSingleton()) {
            LOG.debug("Stopping producer: {}", this);
        } else {
            LOG.trace("Stopping producer: {}", this);
        }
    }

};