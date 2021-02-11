//,temp,Logger.java,112,122,temp,Logger.java,83,94
//,2
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