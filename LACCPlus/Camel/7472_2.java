//,temp,HazelcastDefaultComponent.java,186,233,temp,HazelcastDefaultComponent.java,134,184
//,3
public class xxx {
    protected HazelcastInstance getOrCreateHzInstance(CamelContext context, Map<String, Object> parameters) throws Exception {
        HazelcastInstance hzInstance = null;
        Config config = null;

        // Query param named 'hazelcastInstance' (if exists) overrides the instance that was set
        hzInstance = resolveAndRemoveReferenceParameter(parameters, HAZELCAST_INSTANCE_PARAM, HazelcastInstance.class);

        // Check if an already created instance is given then just get instance by its name.
        if (hzInstance == null && parameters.get(HAZELCAST_INSTANCE_NAME_PARAM) != null) {
            hzInstance = Hazelcast.getHazelcastInstanceByName((String) parameters.get(HAZELCAST_INSTANCE_NAME_PARAM));
        }

        // If instance neither supplied nor found by name, try to lookup its config
        // as reference or as xml configuration file.
        if (hzInstance == null) {
            config = resolveAndRemoveReferenceParameter(parameters, HAZELCAST_CONFIGU_PARAM, Config.class);
            if (config == null) {
                String configUri = getAndRemoveParameter(parameters, HAZELCAST_CONFIGU_URI_PARAM, String.class);
                if (configUri != null) {
                    configUri = getCamelContext().resolvePropertyPlaceholders(configUri);
                }
                if (configUri != null) {
                    InputStream is = ResourceHelper.resolveMandatoryResourceAsInputStream(context, configUri);
                    config = new XmlConfigBuilder(is).build();
                }
            }

            if (hazelcastInstance == null && config == null) {
                config = new XmlConfigBuilder().build();
                // Disable the version check
                config.getProperties().setProperty("hazelcast.version.check.enabled", "false");
                config.getProperties().setProperty("hazelcast.phone.home.enabled", "false");

                hzInstance = Hazelcast.newHazelcastInstance(config);
            } else if (config != null) {
                if (ObjectHelper.isNotEmpty(config.getInstanceName())) {
                    hzInstance = Hazelcast.getOrCreateHazelcastInstance(config);
                } else {
                    hzInstance = Hazelcast.newHazelcastInstance(config);
                }
            }

            if (hzInstance != null) {
                if (this.customHazelcastInstances.add(hzInstance)) {
                    LOGGER.debug("Add managed HZ instance {}", hzInstance.getName());
                }
            }
        }

        return hzInstance == null ? hazelcastInstance : hzInstance;
    }

};