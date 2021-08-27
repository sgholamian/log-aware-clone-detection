//,temp,XACompletionTest.java,486,563,temp,XACompletionTest.java,411,484
//,3
public class xxx {
    @Test
    public void testConsumeAfterAckPreparedRolledbackTopic() throws Exception {

        factory = new ActiveMQXAConnectionFactory(connectionUri + "?jms.prefetchPolicy.all=0");
        factory.setWatchTopicAdvisories(false);

        final ActiveMQTopic destination = new ActiveMQTopic("TEST");

        ActiveMQXAConnection activeMQXAConnection = (ActiveMQXAConnection) factory.createXAConnection();
        activeMQXAConnection.setClientID("durable");
        activeMQXAConnection.start();
        XASession xaSession = activeMQXAConnection.createXASession();

        MessageConsumer consumer = xaSession.createDurableSubscriber(destination, "sub1");
        consumer.close();
        consumer = xaSession.createDurableSubscriber(destination, "sub2");

        sendMessagesTo(10, destination);

        XAResource resource = xaSession.getXAResource();
        resource.recover(XAResource.TMSTARTRSCAN);
        resource.recover(XAResource.TMNOFLAGS);

        dumpMessages();
        Xid tid = createXid();

        resource.start(tid, XAResource.TMNOFLAGS);

        int messagesReceived = 0;

        for (int i = 0; i < 5; i++) {

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
        activeMQXAConnection.close();

        LOG.info("after close");

        broker = restartBroker();

        LOG.info("Try consume... after restart");
        dumpMessages();

        factory = new ActiveMQXAConnectionFactory(connectionUri + "?jms.prefetchPolicy.all=0");
        factory.setWatchTopicAdvisories(false);

        activeMQXAConnection = (ActiveMQXAConnection) factory.createXAConnection();
        activeMQXAConnection.start();
        xaSession = activeMQXAConnection.createXASession();

        XAResource xaResource = xaSession.getXAResource();

        Xid[] xids = xaResource.recover(XAResource.TMSTARTRSCAN);
        xaResource.recover(XAResource.TMNOFLAGS);

        LOG.info("Rollback outcome for ack");
        xaResource.rollback(xids[0]);

        assertTrue("got expected", consumeOnlyN(10,"durable", "sub1", destination));
        assertTrue("got expected", consumeOnlyN(10, "durable", "sub2", destination));
    }

};