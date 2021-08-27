//,temp,XACompletionTest.java,228,309,temp,XACompletionTest.java,154,225
//,3
public class xxx {
    @Test
    public void testStatsAndBrowseAfterAckPreparedRolledback() throws Exception {

        factory = new ActiveMQXAConnectionFactory(
                connectionUri + "?jms.prefetchPolicy.all=0");

        factory.setWatchTopicAdvisories(false);
        sendMessages(10);

        ObjectName queueViewMBeanName = new ObjectName("org.apache.activemq:type=Broker,brokerName=localhost,destinationType=Queue,destinationName=TEST");
        QueueViewMBean proxy = (QueueViewMBean) broker.getManagementContext()
                .newProxyInstance(queueViewMBeanName, QueueViewMBean.class, true);

        ActiveMQXAConnection activeMQXAConnection = (ActiveMQXAConnection) factory.createXAConnection();
        activeMQXAConnection.start();
        XASession xaSession = activeMQXAConnection.createXASession();

        Destination destination = xaSession.createQueue("TEST");
        MessageConsumer consumer = xaSession.createConsumer(destination);

        XAResource resource = xaSession.getXAResource();
        resource.recover(XAResource.TMSTARTRSCAN);
        resource.recover(XAResource.TMNOFLAGS);

        assertEquals("prefetch 0", 0, proxy.getInFlightCount());
        assertEquals("size 0", 10, proxy.getQueueSize());
        assertEquals("size 0", 0, proxy.cursorSize());

        Xid tid = createXid();

        resource.start(tid, XAResource.TMNOFLAGS);

        for (int i = 0; i < 5; i++) {

            Message message = null;
            try {
                message = consumer.receive(2000);
                LOG.info("Received : " + message);
            } catch (Exception e) {
                LOG.debug("Caught exception:", e);
            }
        }

        resource.end(tid, XAResource.TMSUCCESS);
        resource.prepare(tid);

        consumer.close();

        dumpMessages();

        Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return proxy.getInFlightCount() == 0l;
            }
        });
        assertEquals("prefetch", 0, proxy.getInFlightCount());
        assertEquals("size", 10, proxy.getQueueSize());
        assertEquals("cursor size", 0, proxy.cursorSize());

        resource.rollback(tid);

        dumpMessages();

        LOG.info("Try jmx browse... after rollback");

        assertEquals(10, proxy.browseMessages().size());

        assertEquals("prefetch", 0, proxy.getInFlightCount());
        assertEquals("size", 10, proxy.getQueueSize());
        assertEquals("cursor size", 0, proxy.cursorSize());

        LOG.info("Try browse... after");
        Message browsed = regularBrowseFirst();
        assertNotNull("message gone", browsed);

        LOG.info("Try receive... after");
        for (int i = 0; i < 10; i++) {
            Message message = regularReceive("TEST");
            assertNotNull("message gone", message);
        }
    }

};