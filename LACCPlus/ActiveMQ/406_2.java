//,temp,XACompletionTest.java,228,309,temp,XACompletionTest.java,154,225
//,3
public class xxx {
    @Test
    public void testStatsAndBrowseAfterAckPreparedCommitted() throws Exception {

        factory = new ActiveMQXAConnectionFactory(
                connectionUri + "?jms.prefetchPolicy.all=0&jms.redeliveryPolicy.maximumRedeliveries=" + messagesExpected);

        factory.setWatchTopicAdvisories(false);
        sendMessages(messagesExpected);

        ActiveMQXAConnection activeMQXAConnection = (ActiveMQXAConnection) factory.createXAConnection();
        activeMQXAConnection.start();
        XASession xaSession = activeMQXAConnection.createXASession();

        Destination destination = xaSession.createQueue("TEST");
        MessageConsumer consumer = xaSession.createConsumer(destination);

        XAResource resource = xaSession.getXAResource();
        resource.recover(XAResource.TMSTARTRSCAN);
        resource.recover(XAResource.TMNOFLAGS);

        Xid tid = createXid();

        resource.start(tid, XAResource.TMNOFLAGS);

        int messagesReceived = 0;

        for (int i = 0; i < messagesExpected; i++) {

            Message message = null;
            try {
                LOG.debug("Receiving message " + (messagesReceived + 1) + " of " + messagesExpected);
                message = consumer.receive(2000);
                LOG.info("Received : " + message);
                messagesReceived++;
            } catch (Exception e) {
                LOG.debug("Caught exception:", e);
            }
        }

        resource.end(tid, XAResource.TMSUCCESS);
        resource.prepare(tid);

        consumer.close();

        dumpMessages();

        resource.commit(tid, false);

        dumpMessages();

        LOG.info("Try jmx browse... after commit");

        ObjectName queueViewMBeanName = new ObjectName("org.apache.activemq:type=Broker,brokerName=localhost,destinationType=Queue,destinationName=TEST");
        QueueViewMBean proxy = (QueueViewMBean) broker.getManagementContext()
                .newProxyInstance(queueViewMBeanName, QueueViewMBean.class, true);

        assertTrue(proxy.browseMessages().isEmpty());
        assertEquals("prefetch 0", 0, proxy.getInFlightCount());
        assertEquals("size 0", 0, proxy.getQueueSize());

        LOG.info("Try browse... after commit");
        Message browsed = regularBrowseFirst();


        assertNull("message gone", browsed);

        LOG.info("Try receive... after commit");
        Message message = regularReceive("TEST");

        assertNull("message gone", message);

    }

};