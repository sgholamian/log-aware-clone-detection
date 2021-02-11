//,temp,Logger.java,196,206,temp,Logger.java,168,178
//,2
public class xxx {
    public void warn(Object message) {
        if (factory != null) {
            if (logger == null)
                logger = factory.getLogger(clazz);

            logger.warn(message);
        } else {
            if (level <= LEVEL_WARN)
                System.out.println(message);
        }
    }

};