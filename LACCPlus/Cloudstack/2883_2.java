//,temp,Logger.java,180,194,temp,Logger.java,152,166
//,2
public class xxx {
    public void debug(Object message, Throwable exception) {
        if (factory != null) {
            if (logger == null)
                logger = factory.getLogger(clazz);

            logger.debug(message, exception);
        } else {
            if (level <= LEVEL_DEBUG) {
                System.out.println(message);
                if (exception != null) {
                    exception.printStackTrace(System.out);
                }
            }
        }
    }

};