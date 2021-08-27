//,temp,CamelLogger.java,199,218,temp,CamelLogger.java,178,197
//,2
public class xxx {
    public static void log(Logger log, LoggingLevel level, Marker marker, String message) {
        switch (level) {
            case DEBUG:
                log.debug(marker, message);
                break;
            case ERROR:
                log.error(marker, message);
                break;
            case INFO:
                log.info(marker, message);
                break;
            case TRACE:
                log.trace(marker, message);
                break;
            case WARN:
                log.warn(marker, message);
                break;
            default:
        }
    }

};