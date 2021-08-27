//,temp,DefaultErrorHandler.java,76,79,temp,Pipeline.java,145,148
//,2
public class xxx {
    public static void onClassloaded(Logger log) {
        Pipeline dummy = new Pipeline(log);
        log.trace("Loaded {}", dummy.getClass().getSimpleName());
    }

};