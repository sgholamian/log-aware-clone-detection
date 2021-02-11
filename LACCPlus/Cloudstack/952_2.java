//,temp,Logger.java,208,222,temp,Logger.java,196,206
//,3
public class xxx {
    public void error(Object message) {
        if (factory != null) {
            if (logger == null)
                logger = factory.getLogger(clazz);

            logger.error(message);
        } else {
            if (level <= LEVEL_ERROR)
                System.out.println(message);
        }
    }

};