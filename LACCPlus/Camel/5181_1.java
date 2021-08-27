//,temp,BaseMainSupport.java,1108,1165,temp,BaseMainSupport.java,1060,1106
//,3
public class xxx {
    protected void autoConfigurationMainConfiguration(
            CamelContext camelContext, MainConfigurationProperties config, Map<String, String> autoConfiguredProperties)
            throws Exception {
        // load properties
        Properties prop = camelContext.getPropertiesComponent().loadProperties(name -> name.startsWith("camel."));

        // load properties from ENV (override existing)
        if (mainConfigurationProperties.isAutoConfigurationEnvironmentVariablesEnabled()) {
            Properties propENV = helper.loadEnvironmentVariablesAsProperties(new String[] { "camel.main." });
            // special handling of these so remove them
            // ENV variables cannot use dash so replace with dot
            propENV.remove(INITIAL_PROPERTIES_LOCATION.replace('-', '.'));
            propENV.remove(OVERRIDE_PROPERTIES_LOCATION.replace('-', '.'));
            propENV.remove(PROPERTY_PLACEHOLDER_LOCATION.replace('-', '.'));
            if (!propENV.isEmpty()) {
                prop.putAll(propENV);
            }
        }
        // load properties from JVM (override existing)
        if (mainConfigurationProperties.isAutoConfigurationSystemPropertiesEnabled()) {
            Properties propJVM = helper.loadJvmSystemPropertiesAsProperties(new String[] { "camel.main." });
            // special handling of these so remove them
            propJVM.remove(INITIAL_PROPERTIES_LOCATION);
            propJVM.remove(StringHelper.dashToCamelCase(INITIAL_PROPERTIES_LOCATION));
            propJVM.remove(OVERRIDE_PROPERTIES_LOCATION);
            propJVM.remove(StringHelper.dashToCamelCase(OVERRIDE_PROPERTIES_LOCATION));
            propJVM.remove(PROPERTY_PLACEHOLDER_LOCATION);
            propJVM.remove(StringHelper.dashToCamelCase(PROPERTY_PLACEHOLDER_LOCATION));
            if (!propJVM.isEmpty()) {
                prop.putAll(propJVM);
            }
        }

        Map<String, Object> properties = new LinkedHashMap<>();

        for (String key : prop.stringPropertyNames()) {
            if (key.startsWith("camel.main.")) {
                // grab the value
                String value = prop.getProperty(key);
                String option = key.substring(11);
                validateOptionAndValue(key, option, value);
                properties.put(optionKey(option), value);
            }
        }

        if (!properties.isEmpty()) {
            LOG.debug("Auto-configuring main from loaded properties: {}", properties.size());
            setPropertiesOnTarget(camelContext, config, properties, "camel.main.",
                    mainConfigurationProperties.isAutoConfigurationFailFast(), true, autoConfiguredProperties);
        }

        // log which options was not set
        if (!properties.isEmpty()) {
            properties.forEach((k, v) -> {
                LOG.warn("Property not auto-configured: camel.main.{}={} on bean: {}", k, v, config);
            });
        }
    }

};