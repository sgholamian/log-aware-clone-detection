//,temp,XAConsumerTest.java,158,244,temp,XAConsumerTest.java,72,155
//,3
public class xxx {
    public void testPullRequestXAConsumer() throws Exception {

        ActiveMQXAConnectionFactory activeMQConnectionFactory =
                new ActiveMQXAConnectionFactory("admin", "admin", brokerUri + "?trace=true&jms.prefetchPolicy.all=0");
        XAConnection connection = activeMQConnectionFactory.createXAConnection();
        connection.start();

        ActiveMQXAConnectionFactory activeMQConnectionFactoryAutoAck =
                new ActiveMQXAConnectionFactory("admin", "admin", brokerUri + "?trace=true&jms.prefetchPolicy.all=0");
        // allow non xa use of connections
        activeMQConnectionFactoryAutoAck.setXaAckMode(Session.AUTO_ACKNOWLEDGE);
        Connection autoAckConnection = activeMQConnectionFactoryAutoAck.createConnection();
        autoAckConnection.start();

        try {

            LOG.info(">>>INVOKE XA receive with PullRequest Consumer...");

            XASession xaSession = connection.createXASession();
            XAResource xaResource = xaSession.getXAResource();
            Xid xid = createXid();
            xaResource.start(xid, 0);

            Destination destination = xaSession.createQueue("TEST.T2");

            final MessageConsumer messageConsumer = xaSession.createConsumer(destination);
            final CountDownLatch receiveThreadDone = new CountDownLatch(1);

            final CountDownLatch receiveLatch = new CountDownLatch(1);
            // do a message receive

            Thread receiveThread = new Thread(new Runnable() {
                public void run() {
                    try {
                        messageConsumer.receive(600000);
                    } catch (JMSException expected) {
                        receiveLatch.countDown();
                        LOG.info("got expected ex: ", expected);
                    } finally {
                        receiveThreadDone.countDown();
                    }
                }
            });

            receiveThread.start();

            LOG.info(">>>simulate Transaction Rollback");
            xaResource.end(xid, XAResource.TMFAIL);
            xaResource.rollback(xid);

            // send a message after transaction is rolled back.
            LOG.info(">>>Sending message...");

            Session session = autoAckConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Message messageToSend = session.createMessage();
            MessageProducer messageProducer = session.createProducer(destination);
            messageProducer.send(messageToSend);

            receiveThreadDone.await(30, TimeUnit.SECONDS);
            receiveLatch.await(5, TimeUnit.SECONDS);


            // consume with non transacted consumer to verify not autoacked
            messageConsumer.close();
            xaSession.close();

            MessageConsumer messageConsumer1 = session.createConsumer(destination);
            javax.jms.Message message = messageConsumer1.receive(5000);

            assertNotNull("Got message", message);
            LOG.info("Got message on new session", message);
            message.acknowledge();

        } finally {
            LOG.info(">>>Closing Connection");
            if (connection != null) {
                connection.close();
            }
            if (autoAckConnection != null) {
                autoAckConnection.close();
            }
        }

    }

};