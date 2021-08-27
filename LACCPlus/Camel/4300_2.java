//,temp,CamelLogger.java,220,245,temp,CamelLogger.java,157,176
//,3
public class xxx {
    public static void log(Logger log, LoggingLevel level, String message) {
        switch (level) {
            case DEBUG:
                log.debug(message);
                break;
            case ERROR:
                log.error(message);
                break;
            case INFO:
                log.info(message);
                break;
            case TRACE:
                log.trace(message);
                break;
            case WARN:
                log.warn(message);
                break;
            default:
        }
    }

};