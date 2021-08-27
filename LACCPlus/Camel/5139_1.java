//,temp,CamelAnnotationsHandler.java,312,369,temp,CamelAnnotationsHandler.java,172,226
//,3
public class xxx {
    public static void handleUseOverridePropertiesWithPropertiesComponent(
            ConfigurableApplicationContext context, Class<?> testClass)
            throws Exception {
        Collection<Method> methods = CamelSpringTestHelper.getAllMethods(testClass);
        final List<Properties> properties = new LinkedList<>();

        for (Method method : methods) {
            if (AnnotationUtils.findAnnotation(method, UseOverridePropertiesWithPropertiesComponent.class) != null) {
                Class<?>[] argTypes = method.getParameterTypes();
                if (argTypes.length > 0) {
                    throw new IllegalArgumentException(
                            "Method [" + method.getName()
                                                       + "] is annotated with UseOverridePropertiesWithPropertiesComponent but is not a no-argument method.");
                } else if (!Properties.class.isAssignableFrom(method.getReturnType())) {
                    throw new IllegalArgumentException(
                            "Method [" + method.getName()
                                                       + "] is annotated with UseOverridePropertiesWithPropertiesComponent but does not return a java.util.Properties.");
                } else if (!Modifier.isStatic(method.getModifiers())) {
                    throw new IllegalArgumentException(
                            "Method [" + method.getName()
                                                       + "] is annotated with UseOverridePropertiesWithPropertiesComponent but is not static.");
                } else if (!Modifier.isPublic(method.getModifiers())) {
                    throw new IllegalArgumentException(
                            "Method [" + method.getName()
                                                       + "] is annotated with UseOverridePropertiesWithPropertiesComponent but is not public.");
                }

                try {
                    properties.add((Properties) method.invoke(null));
                } catch (Exception e) {
                    throw new RuntimeException(
                            "Method [" + method.getName()
                                               + "] threw exception during evaluation.",
                            e);
                }
            }
        }

        Properties extra = new Properties();
        for (Properties prop : properties) {
            extra.putAll(prop);
        }

        if (!extra.isEmpty()) {
            context.addBeanFactoryPostProcessor(beanFactory -> beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
                @Override
                public Object postProcessBeforeInitialization(Object bean, String beanName) {
                    if (bean instanceof PropertiesComponent) {
                        PropertiesComponent pc = (PropertiesComponent) bean;
                        LOGGER.info("Using {} properties to override any existing properties on the PropertiesComponent",
                                extra.size());
                        pc.setOverrideProperties(extra);
                    }
                    return bean;
                }
            }));
        }
    }

};