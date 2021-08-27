//,temp,JmsEndpointConfigurationTest.java,90,104,temp,JmsEndpointConfigurationTest.java,74,88
//,3
public class xxx {
    @Test
    public void testDurableSharedSubscriber() throws Exception {
        JmsEndpoint endpoint = resolveMandatoryEndpoint(
                "jms:topic:Foo.Bar?subscriptionDurable=true&subscriptionShared=true&subscriptionName=James", JmsEndpoint.class);
        JmsConfiguration configuration = endpoint.getConfiguration();
        assertEquals(true, configuration.isSubscriptionDurable(), "isSubscriptionDurable()");
        assertEquals(true, configuration.isSubscriptionShared(), "isSubscriptionShared()");
        assertEquals("James", configuration.getSubscriptionName(), "getSubscriptionName()");

        JmsConsumer consumer = endpoint.createConsumer(exchange -> LOG.info("Received: " + exchange));
        AbstractMessageListenerContainer listenerContainer = consumer.getListenerContainer();
        assertEquals(true, listenerContainer.isSubscriptionDurable(), "isSubscriptionDurable()");
        assertEquals(true, listenerContainer.isSubscriptionShared(), "isSubscriptionShared()");
        assertEquals("James", listenerContainer.getSubscriptionName(), "getSubscriptionName()");
    }

};