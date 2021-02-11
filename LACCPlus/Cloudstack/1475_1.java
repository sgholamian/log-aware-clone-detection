//,temp,Logger.java,196,206,temp,Logger.java,112,122
//,2
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