//,temp,AMQ2240Test.java,64,85,temp,AMQ2611Test.java,52,66
//,3
public class xxx {
    @Test
    public void testBadVMTransportOptionsBrokerPrefix() throws Exception {
        final String vmUri = vmUri("?broker.XXX=foo&broker.persistent=XXX&broker.useJmx=false");

        LOG.info("creating context with bad URI: {}", vmUri);
        ActiveMQComponent amq = ActiveMQComponent.activeMQComponent(vmUri);

        camelContext = new DefaultCamelContext();
        camelContext.addComponent("activemq", amq);
        final String queueEndpointName = "activemq:queuetest.Queue";
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from(queueEndpointName).bean(Consumer.class, "consume");
            }
        });

        camelContext.start();
        final ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
        assertThrows(CamelExecutionException.class, () -> producerTemplate.sendBody(queueEndpointName, "message"),
                "Should have received an exception from the bad URI.");
    }

};