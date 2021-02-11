//,temp,Logger.java,208,222,temp,Logger.java,96,110
//,2
public class xxx {
    public void error(Object message, Throwable exception) {
        if (factory != null) {
            if (logger == null)
                logger = factory.getLogger(clazz);

            logger.error(message, exception);
        } else {
            if (level <= LEVEL_ERROR) {
                System.out.println(message);
                if (exception != null) {
                    exception.printStackTrace(System.out);
                }
            }
        }
    }

};