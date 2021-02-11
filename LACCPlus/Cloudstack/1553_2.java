//,temp,DatabaseAccessObject.java,76,84,temp,AutoCloseableUtil.java,24,34
//,3
public class xxx {
    public static void closeAutoCloseable(AutoCloseable ac, String message) {
        try {

            if (ac != null) {
                ac.close();
            }

        } catch (Exception e) {
            s_logger.warn("[ignored] " + message, e);
        }
    }

};