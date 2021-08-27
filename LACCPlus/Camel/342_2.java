//,temp,CamelInternalProcessor.java,136,139,temp,CamelInternalProcessor.java,127,134
//,3
public class xxx {
    private CamelInternalProcessor(Logger log) {
        // used for eager loading
        camelContext = null;
        reactiveExecutor = null;
        shutdownStrategy = null;
        AsyncAfterTask task = new AsyncAfterTask(null);
        log.trace("Loaded {}", task.getClass().getSimpleName());
    }

};