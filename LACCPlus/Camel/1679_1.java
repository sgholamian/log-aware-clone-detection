//,temp,DefaultEndpoint.java,487,523,temp,AutowiredLifecycleStrategy.java,123,152
//,3
public class xxx {
    @Override
    protected void doInit() throws Exception {
        ObjectHelper.notNull(getCamelContext(), "camelContext");

        if (autowiredEnabled && getComponent() != null) {
            PropertyConfigurer configurer = getComponent().getEndpointPropertyConfigurer();
            if (configurer instanceof PropertyConfigurerGetter) {
                PropertyConfigurerGetter getter = (PropertyConfigurerGetter) configurer;
                String[] names = getter.getAutowiredNames();
                if (names != null) {
                    for (String name : names) {
                        // is there already a configured value?
                        Object value = getter.getOptionValue(this, name, true);
                        if (value == null) {
                            Class<?> type = getter.getOptionType(name, true);
                            if (type != null) {
                                Set<?> set = camelContext.getRegistry().findByType(type);
                                if (set.size() == 1) {
                                    value = set.iterator().next();
                                }
                            }
                            if (value != null) {
                                boolean hit = configurer.configure(camelContext, this, name, value, true);
                                if (hit) {
                                    if (LOG.isDebugEnabled()) {
                                        LOG.debug(
                                                "Autowired property: {} on endpoint: {} as exactly one instance of type: {} ({}) found in the registry",
                                                name, toString(), type.getName(), value.getClass().getName());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

};