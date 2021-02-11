//,temp,Logger.java,196,206,temp,Logger.java,140,150
//,2
public class xxx {
    public void debug(Object message) {
        if (factory != null) {
            if (logger == null)
                logger = factory.getLogger(clazz);

            logger.debug(message);
        } else {
            if (level <= LEVEL_DEBUG)
                System.out.println(message);
        }
    }

};