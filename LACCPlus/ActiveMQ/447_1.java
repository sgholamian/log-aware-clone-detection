//,temp,FailoverTransactionTest.java,1099,1170,temp,FailoverTransactionTest.java,1044,1097
//,3
public class xxx {
    public void testReDeliveryWhilePending() throws Exception {
        LOG.info("testReDeliveryWhilePending()");
        broker = createBroker(true);
        broker.start();
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("failover:(" + url + ")?jms.consumerFailoverRedeliveryWaitPeriod=10000");
        configureConnectionFactory(cf);
        Connection connection = cf.createConnection();
        connection.start();
        final Session producerSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final Queue destination = producerSession.createQueue(QUEUE_NAME + "?consumer.prefetchSize=0");
        final Session consumerSession = connection.createSession(true, Session.SESSION_TRANSACTED);
        final Session secondConsumerSession = connection.createSession(true, Session.SESSION_TRANSACTED);

        MessageConsumer consumer = consumerSession.createConsumer(destination);

        produceMessage(producerSession, destination);
        Message msg = consumer.receive(20000);
        if (msg == null) {
            AutoFailTestSupport.dumpAllThreads("missing-");
        }
        assertNotNull("got message just produced", msg);

        // add another consumer into the mix that may get the message after restart
        MessageConsumer consumer2 = secondConsumerSession.createConsumer(consumerSession.createQueue(QUEUE_NAME + "?consumer.prefetchSize=1"));

        broker.stop();
        broker = createBroker(false, url);
        broker.start();

        final CountDownLatch commitDone = new CountDownLatch(1);
        final CountDownLatch gotRollback = new CountDownLatch(1);

        final Vector<Exception> exceptions = new Vector<Exception>();

        // commit will fail due to failover with outstanding ack
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            public void run() {
                LOG.info("doing async commit...");
                try {
                    consumerSession.commit();
                } catch (TransactionRolledBackException ex) {
                    gotRollback.countDown();
                } catch (JMSException ex) {
                    exceptions.add(ex);
                } finally {
                    commitDone.countDown();
                }
            }
        });


        assertTrue("commit completed ", commitDone.await(15, TimeUnit.SECONDS));
        assertTrue("got Rollback", gotRollback.await(15, TimeUnit.SECONDS));

        assertTrue("no other exceptions", exceptions.isEmpty());

        // consumer replay is hashmap order dependent on a failover connection state recover so need to deal with both cases
        // consume message from one of the consumers
        Message message = consumer2.receive(2000);
        if (message == null) {
            message = consumer.receive(2000);
        }
        consumerSession.commit();
        secondConsumerSession.commit();

        assertNotNull("got message after rollback", message);

        // no message should be in dlq
        MessageConsumer dlqConsumer = consumerSession.createConsumer(consumerSession.createQueue("ActiveMQ.DLQ"));
        assertNull("nothing in the dlq", dlqConsumer.receive(2000));
        connection.close();
    }

};