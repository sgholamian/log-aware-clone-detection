//,temp,SplunkHECConfiguration.java,81,91,temp,CxfEndpointUtils.java,45,55
//,3
public class xxx {
    public String getHost() {
        if (host == null) {
            try {
                host = HostUtils.getLocalHostName();
            } catch (UnknownHostException e) {
                LOG.warn(e.getMessage(), e);
                host = "unknown";
            }
        }
        return host;
    }

};