//,temp,IOHelper.java,278,294,temp,IOHelper.java,252,268
//,3
public class xxx {
    public static void force(FileOutputStream os, String name, Logger log) {
        try {
            if (os != null) {
                os.getFD().sync();
            }
        } catch (Exception e) {
            if (log == null) {
                // then fallback to use the own Logger
                log = LOG;
            }
            if (name != null) {
                log.warn("Cannot sync FileDescriptor: " + name + ". Reason: " + e.getMessage(), e);
            } else {
                log.warn("Cannot sync FileDescriptor. Reason: {}", e.getMessage(), e);
            }
        }
    }

};