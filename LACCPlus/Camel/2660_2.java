//,temp,DnsActivationPolicy.java,197,205,temp,DnsActivationPolicy.java,176,184
//,2
public class xxx {
    private void startRoutes() {
        for (Map.Entry<String, Route> entry : routes.entrySet()) {
            try {
                startRouteImpl(entry.getValue());
            } catch (Exception e) {
                LOG.warn(entry.getKey(), e);
            }
        }
    }

};