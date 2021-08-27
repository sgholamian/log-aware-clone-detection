//,temp,DnsActivationPolicy.java,197,205,temp,DnsActivationPolicy.java,176,184
//,2
public class xxx {
    private void stopRoutes() {
        for (Map.Entry<String, Route> routeEntry : routes.entrySet()) {
            try {
                stopRouteImpl(routeEntry.getValue());
            } catch (Exception e) {
                LOG.warn(routeEntry.getKey(), e);
            }
        }
    }

};