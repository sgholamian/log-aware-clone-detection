//,temp,XACompletionTest.java,809,938,temp,XACompletionTest.java,693,807
//,3
public class xxx {
    @Test
    public void testMoveInTwoBranchesPreparedAckRecoveryRestartRollback() throws Exception {

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

        final Xid tid = createXid();
        byte[] branch = tid.getBranchQualifier();
        final byte[] branch2 = Arrays.copyOf(branch, branch.length);
        branch2[0] = '!';

        Xid branchTid = new Xid() {
            @Override
            public int getFormatId() {
                return tid.getFormatId();
            }

            @Override
            public byte[] getGlobalTransactionId() {
                return tid.getGlobalTransactionId();
            }

            @Override
            public byte[] getBranchQualifier() {
                return branch2;
            }
        };

        resource.start(tid, XAResource.TMNOFLAGS);

        int messagesReceived = 0;

        Message message = null;

        for (int i = 0; i < messagesExpected; i++) {

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

        ActiveMQXAConnection activeMQXAConnectionSend = (ActiveMQXAConnection) factory.createXAConnection();
        activeMQXAConnectionSend.start();
        XASession xaSessionSend = activeMQXAConnection.createXASession();

        Destination destinationSend = xaSessionSend.createQueue("TEST_MOVE");
        MessageProducer producer = xaSessionSend.createProducer(destinationSend);

        XAResource resourceSend = xaSessionSend.getXAResource();
        resourceSend.start(branchTid, XAResource.TMNOFLAGS);

        ActiveMQMessage toSend = (ActiveMQMessage) xaSessionSend.createTextMessage();
        toSend.setTransactionId(new XATransactionId(branchTid));
        producer.send(toSend);

        resourceSend.end(branchTid, XAResource.TMSUCCESS);
        resourceSend.prepare(branchTid);

        // ack on TEST is prepared
        resource.prepare(tid);

        // send to TEST_MOVE is rolledback
        resourceSend.rollback(branchTid);

        consumer.close();

        LOG.info("Prepared");
        dumpMessages();

        broker = restartBroker();

        LOG.info("New broker");
        dumpMessages();

        ObjectName queueViewMBeanName = new ObjectName("org.apache.activemq:type=Broker,brokerName=localhost,destinationType=Queue,destinationName=TEST");
        QueueViewMBean proxy = (QueueViewMBean) broker.getManagementContext()
                .newProxyInstance(queueViewMBeanName, QueueViewMBean.class, true);

        assertEquals("size", 0, proxy.getQueueSize());

        assertNull(regularReceive("TEST_MOVE"));

        ObjectName queueMoveViewMBeanName = new ObjectName("org.apache.activemq:type=Broker,brokerName=localhost,destinationType=Queue,destinationName=TEST_MOVE");
        QueueViewMBean moveProxy = (QueueViewMBean) broker.getManagementContext()
                .newProxyInstance(queueMoveViewMBeanName, QueueViewMBean.class, true);

        assertEquals("enq", 0, moveProxy.getDequeueCount());
        assertEquals("size", 0, moveProxy.getQueueSize());

        assertEquals("size 0", 0, moveProxy.getQueueSize());

        factory = new ActiveMQXAConnectionFactory(
                connectionUri + "?jms.prefetchPolicy.all=0&jms.redeliveryPolicy.maximumRedeliveries=" + messagesExpected);
        factory.setWatchTopicAdvisories(false);

        activeMQXAConnection = (ActiveMQXAConnection) factory.createXAConnection();
        activeMQXAConnection.start();
        xaSession = activeMQXAConnection.createXASession();

        resource = xaSession.getXAResource();
        resource.rollback(tid);

        assertEquals("size", 1, proxy.getQueueSize());
        assertEquals("c size", 1, proxy.cursorSize());

        assertNotNull(regularReceive("TEST"));

        assertEquals("size", 0, proxy.getQueueSize());
        assertEquals("c size", 0, proxy.cursorSize());
        assertEquals("dq", 1, proxy.getDequeueCount());
    }

};