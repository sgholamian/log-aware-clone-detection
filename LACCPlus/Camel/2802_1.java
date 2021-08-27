//,temp,CamelLogger.java,199,218,temp,CamelLogger.java,178,197
//,2
public class xxx {
    public static void log(Logger log, LoggingLevel level, String message, Throwable th) {
        switch (level) {
            case DEBUG:
                log.debug(message, th);
                break;
            case ERROR:
                log.error(message, th);
                break;
            case INFO:
                log.info(message, th);
                break;
            case TRACE:
                log.trace(message, th);
                break;
            case WARN:
                log.warn(message, th);
                break;
            default:
        }
    }

};