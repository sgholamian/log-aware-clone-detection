//,temp,DefaultErrorHandler.java,67,74,temp,DefaultReactiveExecutor.java,103,107
//,3
public class xxx {
    public static void onClassloaded(Logger log) {
        log.trace("Loaded DefaultReactiveExecutor");
        Worker dummy = new Worker(-1, null);
        log.trace("Loaded {}", dummy.getClass().getName());
    }

};