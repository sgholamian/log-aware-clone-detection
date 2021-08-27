//,temp,CamelLogger.java,220,245,temp,CamelLogger.java,157,176
//,3
public class xxx {
    public static void log(Logger log, LoggingLevel level, Marker marker, String message, Throwable th) {
        if (marker == null) {
            log(log, level, message, th);
            return;
        }

        // marker must be provided
        switch (level) {
            case DEBUG:
                log.debug(marker, message, th);
                break;
            case ERROR:
                log.error(marker, message, th);
                break;
            case INFO:
                log.info(marker, message, th);
                break;
            case TRACE:
                log.trace(marker, message, th);
                break;
            case WARN:
                log.warn(marker, message, th);
                break;
            default:
        }
    }

};