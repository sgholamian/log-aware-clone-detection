//,temp,Logger.java,208,222,temp,Logger.java,124,138
//,2
public class xxx {
    public void info(Object message, Throwable exception) {
        if (factory != null) {
            if (logger == null)
                logger = factory.getLogger(clazz);

            logger.info(message, exception);
        } else {
            if (level <= LEVEL_INFO) {
                System.out.println(message);
                if (exception != null) {
                    exception.printStackTrace(System.out);
                }
            }
        }
    }

};