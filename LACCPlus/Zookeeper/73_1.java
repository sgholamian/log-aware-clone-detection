//,temp,ZooTrace.java,84,90,temp,ZooTrace.java,69,73
//,3
public class xxx {
    static public void logRequest(Logger log, long mask,
            char rp, Request request, String header)
    {
        if (isTraceEnabled(log, mask)) {
            log.trace(header + ":" + rp + request.toString());
        }
    }

};