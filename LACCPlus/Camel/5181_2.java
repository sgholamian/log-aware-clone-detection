//,temp,BaseMainSupport.java,1108,1165,temp,BaseMainSupport.java,1060,1106
//,3
public class xxx {
    protected void autoConfigurationPropertiesComponent(CamelContext camelContext, Map<String, String> autoConfiguredProperties)
            throws Exception {
        // load properties
        Properties prop = camelContext.getPropertiesComponent().loadProperties(name -> name.startsWith("camel."));

        // load properties from ENV (override existing)
        if (mainConfigurationProperties.isAutoConfigurationEnvironmentVariablesEnabled()) {
            Properties propENV = helper.loadEnvironmentVariablesAsProperties(new String[] { "camel.component.properties." });
            if (!propENV.isEmpty()) {
                prop.putAll(propENV);
            }
        }
        // load properties from JVM (override existing)
        if (mainConfigurationProperties.isAutoConfigurationSystemPropertiesEnabled()) {
            Properties propJVM = helper.loadJvmSystemPropertiesAsProperties(new String[] { "camel.component.properties." });
            if (!propJVM.isEmpty()) {
                prop.putAll(propJVM);
            }
        }

        Map<String, Object> properties = new LinkedHashMap<>();

        for (String key : prop.stringPropertyNames()) {
            if (key.startsWith("camel.component.properties.")) {
                int dot = key.indexOf('.', 26);
                String option = dot == -1 ? "" : key.substring(dot + 1);
                String value = prop.getProperty(key, "");
                validateOptionAndValue(key, option, value);
                properties.put(optionKey(option), value);
            }
        }

        if (!properties.isEmpty()) {
            LOG.debug("Auto-configuring properties component from loaded properties: {}", properties.size());
            setPropertiesOnTarget(camelContext, camelContext.getPropertiesComponent(), properties,
                    "camel.component.properties.",
                    mainConfigurationProperties.isAutoConfigurationFailFast(), true, autoConfiguredProperties);
        }

        // log which options was not set
        if (!properties.isEmpty()) {
            properties.forEach((k, v) -> {
                LOG.warn("Property not auto-configured: camel.component.properties.{}={} on object: {}", k, v,
                        camelContext.getPropertiesComponent());
            });
        }
    }

};