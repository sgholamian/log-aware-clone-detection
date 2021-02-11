//,temp,Logger.java,124,138,temp,Logger.java,96,110
//,2
public class xxx {
    public void trace(Object message, Throwable exception) {
        if (factory != null) {
            if (logger == null)
                logger = factory.getLogger(clazz);

            logger.trace(message, exception);
        } else {
            if (level <= LEVEL_TRACE) {
                System.out.println(message);
                if (exception != null) {
                    exception.printStackTrace(System.out);
                }
            }
        }
    }

};