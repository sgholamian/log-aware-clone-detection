//,temp,AMQ2240Test.java,64,85,temp,AMQ2611Test.java,52,66
//,3
public class xxx {
    private void createCamelContext() throws Exception {
        LOG.info("creating context and sending message");
        camelContext = new DefaultCamelContext();
        camelContext.addComponent("activemq", ActiveMQComponent.activeMQComponent(service.serviceAddress()));
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