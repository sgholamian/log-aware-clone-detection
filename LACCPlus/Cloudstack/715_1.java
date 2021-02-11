//,temp,NetUtils.java,120,130,temp,NetUtils.java,108,118
//,2
public class xxx {
    public static String getCanonicalHostName() {
        try {
            InetAddress localAddr = InetAddress.getLocalHost();
            if (localAddr != null) {
                return localAddr.getCanonicalHostName();
            }
        } catch (UnknownHostException e) {
            s_logger.warn("UnknownHostException when trying to get canonical host name. ", e);
        }
        return "localhost";
    }

};