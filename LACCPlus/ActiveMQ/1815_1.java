//,temp,XAConsumerTest.java,158,244,temp,XAConsumerTest.java,72,155
//,3
public class xxx {
    public void testPullRequestXAConsumerSingleConsumer() throws Exception {

        ActiveMQXAConnectionFactory activeMQConnectionFactory =
                new ActiveMQXAConnectionFactory("admin", "admin", brokerUri + "?trace=true&jms.prefetchPolicy.all=0");
        XAConnection connection = activeMQConnectionFactory.createXAConnection();
        connection.start();

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

            {
                XASession xaSessionSend = connection.createXASession();
                XAResource xaResourceSend = xaSessionSend.getXAResource();
                Xid xidSend = createXid();
                xaResourceSend.start(xidSend, 0);

                // send a message after transaction is rolled back.
                LOG.info(">>>Sending message...");

                ActiveMQMessage messageToSend = (ActiveMQMessage) xaSessionSend.createMessage();
                messageToSend.setTransactionId(new XATransactionId(xidSend));
                MessageProducer messageProducer = xaSessionSend.createProducer(destination);
                messageProducer.send(messageToSend);

                xaResourceSend.end(xidSend, XAResource.TMSUCCESS);
                xaResourceSend.commit(xidSend, true);
            }

            receiveThreadDone.await(30, TimeUnit.SECONDS);
            receiveLatch.await(5, TimeUnit.SECONDS);

            // after jms exception we need to close
            messageConsumer.close();

            MessageConsumer messageConsumerTwo = xaSession.createConsumer(destination);
            Xid xidReceiveOk = createXid();
            xaResource.start(xidReceiveOk, 0);

            javax.jms.Message message = messageConsumerTwo.receive(10000);

            assertNotNull("Got message", message);
            LOG.info("Got message on new session", message);

            xaResource.end(xidReceiveOk, XAResource.TMSUCCESS);
            xaResource.commit(xidReceiveOk, true);

        } finally {
            LOG.info(">>>Closing Connection");
            if (connection != null) {
                connection.close();
            }
        }

    }

};