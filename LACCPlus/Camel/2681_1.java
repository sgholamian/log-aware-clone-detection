//,temp,DefaultErrorHandler.java,76,79,temp,Pipeline.java,145,148
//,2
public class xxx {
    public static void onClassloaded(Logger log) {
        DefaultErrorHandler dummy = new DefaultErrorHandler(log);
        log.trace("Loaded {}", dummy.getClass().getName());
    }

};