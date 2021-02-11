//,temp,NetUtils.java,132,139,temp,NetUtils.java,120,130
//,3
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