//,temp,AMQ4656Test.java,91,185,temp,DurableSubscriptionOffline2Test.java,64,170
//,3
public class xxx {
    @Test(timeout = 90000)
    public void testDurableConsumerEnqueueCountWithZeroPrefetch() throws Exception {

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(connectionUri);

        Connection connection = connectionFactory.createConnection();
        connection.setClientID(getClass().getName());
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic("DurableTopic");

        MessageConsumer consumer = session.createDurableSubscriber((Topic) destination, "EnqueueSub");

        final BrokerViewMBean brokerView = brokerService.getAdminView();
        ObjectName subName = brokerView.getDurableTopicSubscribers()[0];

        final DurableSubscriptionViewMBean sub = (DurableSubscriptionViewMBean)
            brokerService.getManagementContext().newProxyInstance(subName, DurableSubscriptionViewMBean.class, true);

        assertEquals(0, sub.getEnqueueCounter());
        assertEquals(0, sub.getDequeueCounter());
        assertEquals(0, sub.getPendingQueueSize());
        assertEquals(0, sub.getDispatchedCounter());
        assertEquals(0, sub.getDispatchedQueueSize());

        consumer.close();

        MessageProducer producer = session.createProducer(destination);
        for (int i = 0; i < 20; i++) {
            producer.send(session.createMessage());
        }
        producer.close();

        consumer = session.createDurableSubscriber((Topic) destination, "EnqueueSub");

        assertTrue("Should be an Active Subscription", Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return brokerView.getDurableTopicSubscribers().length == 1;
            }
        }, TimeUnit.SECONDS.toMillis(30), TimeUnit.MILLISECONDS.toMillis(25)));

        assertTrue("Should all be dispatched", Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return sub.getDispatchedCounter() == 20;
            }
        }, TimeUnit.SECONDS.toMillis(30), TimeUnit.MILLISECONDS.toMillis(25)));

        assertEquals(20, sub.getEnqueueCounter());
        assertEquals(0, sub.getDequeueCounter());
        assertEquals(0, sub.getPendingQueueSize());
        assertEquals(20, sub.getDispatchedCounter());
        assertEquals(20, sub.getDispatchedQueueSize());

        LOG.info("Pending Queue Size with no receives: {}", sub.getPendingQueueSize());

        assertNotNull(consumer.receive(1000));
        assertNotNull(consumer.receive(1000));

        consumer.close();

        LOG.info("Pending Queue Size with two receives: {}", sub.getPendingQueueSize());

        assertTrue("Should be an Active Subscription", Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return brokerView.getInactiveDurableTopicSubscribers().length == 1;
            }
        }, TimeUnit.SECONDS.toMillis(30), TimeUnit.MILLISECONDS.toMillis(25)));

        final DurableSubscriptionViewMBean inactive = (DurableSubscriptionViewMBean)
            brokerService.getManagementContext().newProxyInstance(subName, DurableSubscriptionViewMBean.class, true);

        assertTrue("Should all be dispatched", Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return inactive.getDequeueCounter() == 2;
            }
        }, TimeUnit.SECONDS.toMillis(30), TimeUnit.MILLISECONDS.toMillis(25)));

        assertEquals(20, inactive.getEnqueueCounter());
        assertEquals(2, inactive.getDequeueCounter());
        assertEquals(18, inactive.getPendingQueueSize());
        assertEquals(20, inactive.getDispatchedCounter());
        assertEquals(0, inactive.getDispatchedQueueSize());

        session.close();
        connection.close();
    }

};