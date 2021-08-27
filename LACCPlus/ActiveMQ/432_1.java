//,temp,AbortSlowConsumer0Test.java,78,135,temp,AmqpSlowReceiverTest.java,85,142
//,3
public class xxx {
    @Test
    public void testSlowConsumerIsAbortedViaJmx() throws Exception {
        underTest.setMaxSlowDuration(60*1000); // so jmx does the abort
        startConsumers(withPrefetch(2, destination));
        Entry<MessageConsumer, MessageIdList> consumertoAbort = consumers.entrySet().iterator().next();
        consumertoAbort.getValue().setProcessingDelay(8 * 1000);
        for (Connection c : connections) {
            c.setExceptionListener(this);
        }
        startProducers(destination, 100);

        consumertoAbort.getValue().assertMessagesReceived(1);

        ActiveMQDestination amqDest = (ActiveMQDestination)destination;
        ObjectName destinationViewMBean = new ObjectName("org.apache.activemq:destinationType=" +
                (amqDest.isTopic() ? "Topic" : "Queue") +",destinationName="
                + amqDest.getPhysicalName() + ",type=Broker,brokerName=localhost");

        DestinationViewMBean queue = (DestinationViewMBean) broker.getManagementContext().newProxyInstance(destinationViewMBean, DestinationViewMBean.class, true);
        ObjectName slowConsumerPolicyMBeanName = queue.getSlowConsumerStrategy();

        assertNotNull(slowConsumerPolicyMBeanName);

        AbortSlowConsumerStrategyViewMBean abortPolicy = (AbortSlowConsumerStrategyViewMBean)
                broker.getManagementContext().newProxyInstance(slowConsumerPolicyMBeanName, AbortSlowConsumerStrategyViewMBean.class, true);

        TimeUnit.SECONDS.sleep(3);

        TabularData slowOnes = abortPolicy.getSlowConsumers();
        assertEquals("one slow consumers", 1, slowOnes.size());

        LOG.info("slow ones:"  + slowOnes);

        CompositeData slowOne = (CompositeData) slowOnes.values().iterator().next();
        LOG.info("Slow one: " + slowOne);

        assertTrue("we have an object name", slowOne.get("subscription") instanceof ObjectName);
        abortPolicy.abortConsumer((ObjectName)slowOne.get("subscription"));

        consumertoAbort.getValue().assertAtMostMessagesReceived(1);

        slowOnes = abortPolicy.getSlowConsumers();
        assertEquals("no slow consumers left", 0, slowOnes.size());

        // verify mbean gone with destination
        if (topic) {
            broker.getAdminView().removeTopic(amqDest.getPhysicalName());
        } else {
            broker.getAdminView().removeQueue(amqDest.getPhysicalName());
        }
        try {
            abortPolicy.getSlowConsumers();
            fail("expect not found post destination removal");
        } catch(UndeclaredThrowableException expected) {
            assertTrue("correct exception: " + expected.getCause(),
                    expected.getCause() instanceof InstanceNotFoundException);
        }
    }

};