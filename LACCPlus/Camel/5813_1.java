//,temp,CamelSpringTestContextLoader.java,93,108,temp,CamelSpringTestContextLoader.java,61,80
//,3
public class xxx {
    @Override
    public ApplicationContext loadContext(String... locations) throws Exception {
        Class<?> testClass = getTestClass();

        if (LOG.isDebugEnabled()) {
            LOG.debug("Loading ApplicationContext for locations [" + StringUtils.arrayToCommaDelimitedString(locations) + "].");
        }

        try {
            GenericApplicationContext context = createContext(testClass, null);
            loadBeanDefinitions(context, testClass, locations);
            return loadContext(context, testClass);
        } finally {
            CamelAnnotationsHandler.cleanup(testClass);
        }
    }

};