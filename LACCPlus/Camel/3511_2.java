//,temp,CamelAnnotationsHandler.java,286,303,temp,CamelAnnotationsHandler.java,263,277
//,3
public class xxx {
    public static void handleMockEndpoints(ConfigurableApplicationContext context, Class<?> testClass) throws Exception {
        if (testClass.isAnnotationPresent(MockEndpoints.class)) {
            final String mockEndpoints = testClass.getAnnotation(MockEndpoints.class).value();
            CamelSpringTestHelper.doToSpringCamelContexts(context, new CamelSpringTestHelper.DoToSpringCamelContextsStrategy() {

                public void execute(String contextName, SpringCamelContext camelContext)
                        throws Exception {
                    LOGGER.info("Enabling auto mocking of endpoints matching pattern [{}] on CamelContext with name [{}].",
                            mockEndpoints, contextName);
                    camelContext.adapt(ExtendedCamelContext.class)
                            .registerEndpointCallback(new InterceptSendToMockEndpointStrategy(mockEndpoints));
                }
            });
        }
    }

};