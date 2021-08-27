//,temp,HealthCheckHelper.java,161,171,temp,HealthCheckHelper.java,141,151
//,2
public class xxx {
    public static Optional<HealthCheck.Result> invoke(CamelContext camelContext, String id, Map<String, Object> options) {
        final HealthCheckRegistry registry = HealthCheckRegistry.get(camelContext);

        if (registry != null) {
            return registry.getCheck(id).map(check -> check.call(options));
        } else {
            LOGGER.debug("No health check source found");
        }

        return Optional.empty();
    }

};