//,temp,IOHelper.java,336,352,temp,MllpClientResource.java,133,151
//,3
public class xxx {
    public static void close(Closeable closeable, String name, Logger log) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                if (log == null) {
                    // then fallback to use the own Logger
                    log = LOG;
                }
                if (name != null) {
                    log.warn("Cannot close: " + name + ". Reason: " + e.getMessage(), e);
                } else {
                    log.warn("Cannot close. Reason: {}", e.getMessage(), e);
                }
            }
        }
    }

};