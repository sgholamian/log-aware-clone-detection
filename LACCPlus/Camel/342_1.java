//,temp,CamelInternalProcessor.java,136,139,temp,CamelInternalProcessor.java,127,134
//,3
public class xxx {
    public static void onClassloaded(Logger log) {
        CamelInternalProcessor dummy = new CamelInternalProcessor(log);
        log.trace("Loaded {}", dummy.getClass().getSimpleName());
    }

};