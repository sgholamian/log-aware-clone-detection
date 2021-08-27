//,temp,CamelSpringTestContextLoader.java,93,108,temp,CamelSpringTestContextLoader.java,61,80
//,3
public class xxx {
    @Override
    public ApplicationContext loadContext(MergedContextConfiguration mergedConfig) throws Exception {
        Class<?> testClass = getTestClass();

        if (LOG.isDebugEnabled()) {
            LOG.debug("Loading ApplicationContext for merged context configuration [{}].", mergedConfig);
        }

        try {
            GenericApplicationContext context = createContext(testClass, mergedConfig);
            prepareContext(context, mergedConfig);
            loadBeanDefinitions(context, mergedConfig);
            AnnotationConfigUtils.registerAnnotationConfigProcessors(context);
            customizeContext(context, mergedConfig);

            return loadContext(context, testClass);
        } finally {
            CamelAnnotationsHandler.cleanup(testClass);
        }
    }

};