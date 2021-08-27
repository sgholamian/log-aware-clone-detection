//,temp,IOHelper.java,278,294,temp,IOHelper.java,252,268
//,3
public class xxx {
    public static void force(FileChannel channel, String name, Logger log) {
        try {
            if (channel != null) {
                channel.force(true);
            }
        } catch (Exception e) {
            if (log == null) {
                // then fallback to use the own Logger
                log = LOG;
            }
            if (name != null) {
                log.warn("Cannot force FileChannel: " + name + ". Reason: " + e.getMessage(), e);
            } else {
                log.warn("Cannot force FileChannel. Reason: {}", e.getMessage(), e);
            }
        }
    }

};