//,temp,AMQ2611Test.java,51,65,temp,CamelVMTransportRoutingTest.java,140,156
//,3
public class xxx {
    private void createCamelContext() throws Exception {

        final String fromEndpoint = "activemq:topic:" + SENDER_TOPIC;
        final String toEndpoint = "activemq:topic:" + RECEIVER_TOPIC;

        log.info("creating context and sending message");
        camelContext = new DefaultCamelContext();
        camelContext.addComponent("activemq",
                ActiveMQComponent.activeMQComponent("vm://localhost?create=false&waitForStart=10000"));
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(fromEndpoint).to(toEndpoint);
            }
        });
        camelContext.start();
    }

};