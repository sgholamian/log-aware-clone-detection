//,temp,CamelAnnotationsHandler.java,291,300,temp,CamelAnnotationsHandler.java,268,274
//,3
public class xxx {
                public void execute(String contextName, SpringCamelContext camelContext)
                        throws Exception {
                    LOGGER.info("Enabling auto mocking of endpoints matching pattern [{}] on CamelContext with name [{}].",
                            mockEndpoints, contextName);
                    camelContext.adapt(ExtendedCamelContext.class)
                            .registerEndpointCallback(new InterceptSendToMockEndpointStrategy(mockEndpoints));
                }

};