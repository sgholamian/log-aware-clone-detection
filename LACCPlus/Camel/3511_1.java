//,temp,CamelAnnotationsHandler.java,286,303,temp,CamelAnnotationsHandler.java,263,277
//,3
public class xxx {
    public static void handleMockEndpointsAndSkip(ConfigurableApplicationContext context, Class<?> testClass) throws Exception {
        if (testClass.isAnnotationPresent(MockEndpointsAndSkip.class)) {
            final String mockEndpoints = testClass.getAnnotation(MockEndpointsAndSkip.class).value();
            CamelSpringTestHelper.doToSpringCamelContexts(context, new CamelSpringTestHelper.DoToSpringCamelContextsStrategy() {

                public void execute(String contextName, SpringCamelContext camelContext)
                        throws Exception {
                    // resolve the property place holders of the mockEndpoints
                    String mockEndpointsValue = camelContext.resolvePropertyPlaceholders(mockEndpoints);
                    LOGGER.info(
                            "Enabling auto mocking and skipping of endpoints matching pattern [{}] on CamelContext with name [{}].",
                            mockEndpointsValue, contextName);
                    camelContext.adapt(ExtendedCamelContext.class)
                            .registerEndpointCallback(new InterceptSendToMockEndpointStrategy(mockEndpointsValue, true));
                }
            });
        }
    }

};