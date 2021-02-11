//,temp,Logger.java,180,194,temp,Logger.java,124,138
//,2
public class xxx {
    public void warn(Object message, Throwable exception) {
        if (factory != null) {
            if (logger == null)
                logger = factory.getLogger(clazz);

            logger.warn(message, exception);
        } else {
            if (level <= LEVEL_WARN) {
                System.out.println(message);
                if (exception != null) {
                    exception.printStackTrace(System.out);
                }
            }
        }
    }

};