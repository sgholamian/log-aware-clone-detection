//,temp,MetricsComponent.java,95,104,temp,MicrometerUtils.java,59,68
//,2
public class xxx {
    static MetricRegistry getOrCreateMetricRegistry(Registry camelRegistry, String registryName) {
        LOG.debug("Looking up MetricRegistry from Camel Registry for name \"{}\"", registryName);
        MetricRegistry result = getMetricRegistryFromCamelRegistry(camelRegistry, registryName);
        if (result == null) {
            LOG.debug("MetricRegistry not found from Camel Registry for name \"{}\"", registryName);
            LOG.info("Creating new default MetricRegistry");
            result = createMetricRegistry();
        }
        return result;
    }

};