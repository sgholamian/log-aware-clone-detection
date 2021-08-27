//,temp,MetricsComponent.java,95,104,temp,MicrometerUtils.java,59,68
//,2
public class xxx {
    public static MeterRegistry getOrCreateMeterRegistry(Registry camelRegistry, String registryName) {
        LOG.debug("Looking up MeterRegistry from Camel Registry for name \"{}\"", registryName);
        MeterRegistry result = getMeterRegistryFromCamelRegistry(camelRegistry, registryName);
        if (result == null) {
            LOG.debug("MeterRegistry not found from Camel Registry for name \"{}\"", registryName);
            LOG.info("Creating new default MeterRegistry");
            result = createMeterRegistry();
        }
        return result;
    }

};