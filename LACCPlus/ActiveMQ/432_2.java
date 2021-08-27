//,temp,AbortSlowConsumer0Test.java,78,135,temp,AmqpSlowReceiverTest.java,85,142
//,3
public class xxx {
    @Test
    public void testSlowConsumerIsAbortedViaJmx() throws Exception {
        strategy.setMaxSlowDuration(60*1000); // so jmx does the abort

        AmqpClient client = createAmqpClient();
        AmqpConnection connection = trackConnection(client.connect());
        AmqpSession session = connection.createSession();
        final AmqpReceiver receiver = session.createReceiver("queue://" + getTestName());
        receiver.flow(100);

        sendMessages(getTestName(), 100, false);

        AmqpMessage message = receiver.receive(5, TimeUnit.SECONDS);
        assertNotNull(message);
        message.accept();

        QueueViewMBean queue = getProxyToQueue(getTestName());
        ObjectName slowConsumerPolicyMBeanName = queue.getSlowConsumerStrategy();
        assertNotNull(slowConsumerPolicyMBeanName);

        AbortSlowConsumerStrategyViewMBean abortPolicy = (AbortSlowConsumerStrategyViewMBean)
                brokerService.getManagementContext().newProxyInstance(slowConsumerPolicyMBeanName, AbortSlowConsumerStrategyViewMBean.class, true);

        TimeUnit.SECONDS.sleep(6);

        TabularData slowOnes = abortPolicy.getSlowConsumers();
        assertEquals("one slow consumers", 1, slowOnes.size());

        LOG.info("slow ones:"  + slowOnes);

        CompositeData slowOne = (CompositeData) slowOnes.values().iterator().next();
        LOG.info("Slow one: " + slowOne);

        assertTrue("we have an object name", slowOne.get("subscription") instanceof ObjectName);
        abortPolicy.abortConsumer((ObjectName)slowOne.get("subscription"));

        assertTrue("Receiver should be closed", Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return receiver.isClosed();
            }
        }));

        slowOnes = abortPolicy.getSlowConsumers();
        assertEquals("no slow consumers left", 0, slowOnes.size());

        // verify mbean gone with destination
        brokerService.getAdminView().removeQueue(getTestName());

        try {
            abortPolicy.getSlowConsumers();
            fail("expect not found post destination removal");
        } catch(UndeclaredThrowableException expected) {
            assertTrue("correct exception: " + expected.getCause(),
                    expected.getCause() instanceof InstanceNotFoundException);
        }
    }

};