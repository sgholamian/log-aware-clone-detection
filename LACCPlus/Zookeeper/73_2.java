//,temp,ZooTrace.java,84,90,temp,ZooTrace.java,69,73
//,3
public class xxx {
    public static void logTraceMessage(Logger log, long mask, String msg) {
        if (isTraceEnabled(log, mask)) {
            log.trace(msg);
        }
    }

};