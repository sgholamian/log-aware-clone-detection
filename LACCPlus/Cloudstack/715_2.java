//,temp,NetUtils.java,120,130,temp,NetUtils.java,108,118
//,2
public class xxx {
    public static String getHostName() {
        try {
            final InetAddress localAddr = InetAddress.getLocalHost();
            if (localAddr != null) {
                return localAddr.getHostName();
            }
        } catch (final UnknownHostException e) {
            s_logger.warn("UnknownHostException when trying to get host name. ", e);
        }
        return "localhost";
    }

};