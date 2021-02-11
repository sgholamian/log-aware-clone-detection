//,temp,Logger.java,124,138,temp,Logger.java,112,122
//,3
public class xxx {
    public void info(Object message) {
        if (factory != null) {
            if (logger == null)
                logger = factory.getLogger(clazz);

            logger.info(message);
        } else {
            if (level <= LEVEL_INFO)
                System.out.println(message);
        }
    }

};