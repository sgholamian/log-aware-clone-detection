//,temp,CamelAnnotationsHandler.java,291,300,temp,CamelAnnotationsHandler.java,268,274
//,3
public class xxx {
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

};