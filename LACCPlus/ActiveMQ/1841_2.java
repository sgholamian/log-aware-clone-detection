//,temp,FailoverReadInactivityBlockWriteTimeoutClientTest.java,56,146,temp,SoWriteTimeoutClientTest.java,51,101
//,3
public class xxx {
    public void testSendWithClientWriteTimeout() throws Exception {
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

        ActiveMQConnectionFactory pFactory = new ActiveMQConnectionFactory("failover:(" + proxy.getUrl() + "?soWriteTimeout=4000&sleep=500)?jms.useAsyncSend=true&trackMessages=true&maxCacheSize=6638400");
        final Connection pc = pFactory.createConnection();
        pc.start();
        proxy.pause();

        final int messageCount = 20;
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    sendMessages(pc, dest, messageCount);
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        });

        // wait for timeout and reconnect
        TimeUnit.SECONDS.sleep(8);
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