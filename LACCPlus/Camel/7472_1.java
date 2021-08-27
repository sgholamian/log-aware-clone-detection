//,temp,HazelcastDefaultComponent.java,186,233,temp,HazelcastDefaultComponent.java,134,184
//,3
public class xxx {
    protected HazelcastInstance getOrCreateHzClientInstance(CamelContext context, Map<String, Object> parameters)
            throws Exception {
        HazelcastInstance hzInstance = null;
        ClientConfig config = null;

        // Query param named 'hazelcastInstance' (if exists) overrides the instance that was set
        hzInstance = resolveAndRemoveReferenceParameter(parameters, HAZELCAST_INSTANCE_PARAM, HazelcastInstance.class);

        // Check if an already created instance is given then just get instance by its name.
        if (hzInstance == null && parameters.get(HAZELCAST_INSTANCE_NAME_PARAM) != null) {
            hzInstance = HazelcastClient.getHazelcastClientByName((String) parameters.get(HAZELCAST_INSTANCE_NAME_PARAM));
        }

        // If instance neither supplied nor found by name, try to lookup its config
        // as reference or as xml configuration file.
        if (hzInstance == null) {
            config = resolveAndRemoveReferenceParameter(parameters, HAZELCAST_CONFIGU_PARAM, ClientConfig.class);
            if (config == null) {
                String configUri = getAndRemoveParameter(parameters, HAZELCAST_CONFIGU_URI_PARAM, String.class);
                if (configUri != null) {
                    configUri = getCamelContext().resolvePropertyPlaceholders(configUri);
                }
                if (configUri != null) {
                    InputStream is = ResourceHelper.resolveMandatoryResourceAsInputStream(context, configUri);
                    config = new XmlClientConfigBuilder(is).build();
                }
            }

            if (hazelcastInstance == null && config == null) {
                config = new XmlClientConfigBuilder().build();
                // Disable the version check
                config.getProperties().setProperty("hazelcast.version.check.enabled", "false");
                config.getProperties().setProperty("hazelcast.phone.home.enabled", "false");

                hzInstance = HazelcastClient.newHazelcastClient(config);
            } else if (config != null) {
                hzInstance = HazelcastClient.newHazelcastClient(config);
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