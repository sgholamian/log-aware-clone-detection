//,temp,FailoverTransactionTest.java,1099,1170,temp,FailoverTransactionTest.java,1044,1097
//,3
public class xxx {
    public void testWaitForMissingRedeliveries() throws Exception {
        LOG.info("testWaitForMissingRedeliveries()");
        broker = createBroker(true);
        broker.start();
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("failover:(" + url + ")?jms.consumerFailoverRedeliveryWaitPeriod=30000");
        configureConnectionFactory(cf);
        Connection connection = cf.createConnection();
        connection.start();
        final Session producerSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final Queue destination = producerSession.createQueue(QUEUE_NAME);
        final Session consumerSession = connection.createSession(true, Session.SESSION_TRANSACTED);
        MessageConsumer consumer = consumerSession.createConsumer(destination);

        produceMessage(producerSession, destination);
        Message msg = consumer.receive(20000);
        if (msg == null) {
            AutoFailTestSupport.dumpAllThreads("missing-");
        }
        assertNotNull("got message just produced", msg);

        broker.stop();
        broker = createBroker(false, url);
        // use empty jdbc store so that wait for re-deliveries occur when failover resumes
        setPersistenceAdapter(broker, PersistenceAdapterChoice.JDBC);
        broker.start();

        final CountDownLatch commitDone = new CountDownLatch(1);
        final CountDownLatch gotException = new CountDownLatch(1);
        // will block pending re-deliveries
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            public void run() {
                LOG.info("doing async commit...");
                try {
                    consumerSession.commit();
                } catch (JMSException ignored) {
                    ignored.printStackTrace();
                    gotException.countDown();
                } finally {
                    commitDone.countDown();
                }

            }
        });

        broker.stop();
        broker = createBroker(false, url);
        broker.start();

        assertTrue("commit was successful", commitDone.await(30, TimeUnit.SECONDS));
        assertTrue("got exception on commit", gotException.await(30, TimeUnit.SECONDS));

        assertNotNull("should get failed committed message", consumer.receive(5000));
        connection.close();
    }

};