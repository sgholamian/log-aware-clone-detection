//,temp,NetUtils.java,132,139,temp,NetUtils.java,120,130
//,3
public class xxx {
    public static InetAddress getLocalInetAddress() {
        try {
            return InetAddress.getLocalHost();
        } catch (final UnknownHostException e) {
            s_logger.warn("UnknownHostException in getLocalInetAddress().", e);
            return null;
        }
    }

};