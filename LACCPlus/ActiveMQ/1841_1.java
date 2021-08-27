//,temp,FailoverReadInactivityBlockWriteTimeoutClientTest.java,56,146,temp,SoWriteTimeoutClientTest.java,51,101
//,3
public class xxx {
    public void testBlockedFailoverSendWillReactToReadInactivityTimeout() throws Exception {
        final ActiveMQQueue dest = new ActiveMQQueue("testClientWriteTimeout");
        messageTextPrefix = initMessagePrefix(80*1024);

        URI tcpBrokerUri = URISupport.removeQuery(broker.getTransportConnectors().get(0).getConnectUri());
        LOG.info("consuming using uri: " + tcpBrokerUri);

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(tcpBrokerUri);
        Connection c = factory.createConnection();
        c.start();
        Session session = c.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createConsumer(dest);

        SocketProxy proxy = new SocketProxy();
        proxy.setTarget(tcpBrokerUri);
        proxy.open();

        ActiveMQConnectionFactory pFactory = new ActiveMQConnectionFactory("failover:(" + proxy.getUrl() + "?wireFormat.maxInactivityDuration=5000&ignoreRemoteWireFormat=true)?jms.useAsyncSend=true&trackMessages=true&maxCacheSize=6638400");
        final ActiveMQConnection pc = (ActiveMQConnection) pFactory.createConnection();
        final AtomicInteger interruptCounter = new AtomicInteger(0);
        pc.addTransportListener(new TransportListener() {
            @Override
            public void onCommand(Object command) {

            }

            @Override
            public void onException(IOException error) {
                LOG.info("Got: " + error);

            }

            @Override
            public void transportInterupted() {
                interruptCounter.incrementAndGet();
            }

            @Override
            public void transportResumed() {

            }
        });
        pc.start();


        final int messageCount = 200;
        final CountDownLatch sentOne = new CountDownLatch(1);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    Session session = pc.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    MessageProducer producer = session.createProducer(dest);
                    for (int i = 0; i < messageCount; i++) {
                        producer.send(session.createTextMessage(messageTextPrefix  + i));
                        sentOne.countDown();
                    }
                    producer.close();
                    session.close();
                    LOG.info("Done with send of: " + messageCount);
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        });

        sentOne.await(5, TimeUnit.SECONDS);
        proxy.pause();

        assertTrue("Got interrupted", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return interruptCounter.get() > 0;
            }
        }));

        proxy.goOn();
        for (int i=0; i<messageCount; i++) {
            assertNotNull("Got message " + i  + " after reconnect", consumer.receive(5000));
        }

        assertTrue("no pending messages when done", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {

                LOG.info("current total message count: " + broker.getAdminView().getTotalMessageCount());
                return broker.getAdminView().getTotalMessageCount() == 0;
            }
        }));
    }

};