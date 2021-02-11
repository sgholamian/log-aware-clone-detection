//,temp,Logger.java,112,122,temp,Logger.java,83,94
//,2
public class xxx {
    public void trace(Object message) {

        if (factory != null) {
            if (logger == null)
                logger = factory.getLogger(clazz);

            logger.trace(message);
        } else {
            if (level <= LEVEL_TRACE)
                System.out.println(message);
        }
    }

};