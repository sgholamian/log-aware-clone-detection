//,temp,CitrixResourceBase.java,3991,4002,temp,XenServerConnectionPool.java,173,183
//,3
public class xxx {
    public URL getURL(String ip) {
        try {
            return new URL("https://" + ip);
        } catch (Exception e) {
            String msg = "Unable to convert IP " + ip + " to URL due to " + e.toString();
            if (s_logger.isDebugEnabled()) {
                s_logger.debug(msg);
            }
            throw new CloudRuntimeException(msg, e);
        }
    }

};