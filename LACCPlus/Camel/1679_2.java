//,temp,DefaultEndpoint.java,487,523,temp,AutowiredLifecycleStrategy.java,123,152
//,3
public class xxx {
    private void autwire(String name, String kind, Object target) {
        PropertyConfigurer pc = camelContext.getConfigurerResolver().resolvePropertyConfigurer(name + "-" + kind, camelContext);
        if (pc instanceof PropertyConfigurerGetter) {
            PropertyConfigurerGetter getter = (PropertyConfigurerGetter) pc;
            String[] names = getter.getAutowiredNames();
            if (names != null) {
                for (String option : names) {
                    // is there already a configured value?
                    Object value = getter.getOptionValue(target, option, true);
                    if (value == null) {
                        Class<?> type = getter.getOptionType(option, true);
                        if (type != null) {
                            Set<?> set = camelContext.getRegistry().findByType(type);
                            if (set.size() == 1) {
                                value = set.iterator().next();
                            }
                        }
                        if (value != null) {
                            boolean hit = pc.configure(camelContext, target, option, value, true);
                            if (hit) {
                                LOG.info(
                                        "Autowired property: {} on {}: {} as exactly one instance of type: {} ({}) found in the registry",
                                        option, kind, name, type.getName(), value.getClass().getName());
                            }
                        }
                    }
                }
            }
        }
    }

};