//,temp,DefaultErrorHandler.java,67,74,temp,DefaultReactiveExecutor.java,103,107
//,3
public class xxx {
    private DefaultErrorHandler(Logger log) {
        // used for eager loading
        super(log);
        SimpleTask dummy = new SimpleTask();
        log.trace("Loaded {}", dummy.getClass().getName());
        RedeliveryTask dummy2 = new RedeliveryTask();
        log.trace("Loaded {}", dummy2.getClass().getName());
    }

};