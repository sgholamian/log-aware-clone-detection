//,temp,AMQ2611Test.java,51,65,temp,CamelVMTransportRoutingTest.java,140,156
//,3
public class xxx {
    private void createCamelContext() throws Exception {
        log.info("creating context and sending message");
        camelContext = new DefaultCamelContext();
        camelContext.addComponent("activemq", ActiveMQComponent.activeMQComponent(BROKER_URL));
        final String queueEndpointName = "activemq:queue" + QUEUE_NAME;
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(queueEndpointName).bean(Consumer.class, "consume");
            }
        });
        camelContext.start();
        final ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
        producerTemplate.sendBody(queueEndpointName, "message");
    }

};