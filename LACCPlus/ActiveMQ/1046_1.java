//,temp,DurableSubsOfflineSelectorIndexUseTest.java,107,195,temp,DurableSubsOfflineSelectorConcurrentConsumeIndexUseTest.java,113,226
//,3
public class xxx {
    public void testIndexPageUsage() throws Exception {
        Connection con = createConnection();
        Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        session.createDurableSubscriber(topic, "true", "filter = 'true'", true);
        session.close();

        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        session.createDurableSubscriber(topic, "false", "filter = 'false'", true);
        session.close();

        con.close();

        // send messages
        final Connection sendCon = createConnection("send");
        final Session sendSession = sendCon.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final MessageProducer producer = sendSession.createProducer(null);

        Thread sendThread = new Thread() {
            public void run() {
                try {

                    for (int i = 0; i < messageCount; i++) {
                        boolean filter = i % 2 == 1;
                        Message message = sendSession.createMessage();
                        message.setStringProperty("filter", filter ? "true" : "false");
                        producer.send(topic, message);

                        if (i > 0 && i % 1000 == 0) {
                            LOG.info("Sent:" + i);
                        }
                    }
                    sendSession.close();
                    sendCon.close();
                } catch (Exception e) {
                    exceptions.add(e);
                }
            }
        };
        sendThread.start();

        sendThread.join();

        // settle with sent messages
        TimeUnit.SECONDS.sleep(4);

        // consume messages
        con = createConnection();
        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);

        MessageConsumer consumerTrue = session.createDurableSubscriber(topic, "true", "filter = 'true'", true);
        Listener listenerT = new Listener();
        consumerTrue.setMessageListener(listenerT);

        waitFor(listenerT, messageCount / 2);

        MessageConsumer consumerFalse = session.createDurableSubscriber(topic, "false", "filter = 'false'", true);
        Listener listenerF = new Listener();
        consumerFalse.setMessageListener(listenerF);

        waitFor(listenerF, messageCount / 2);

        assertEquals(messageCount / 2, listenerT.count);
        assertEquals(messageCount / 2, listenerF.count);

        consumerTrue.close();
        session.unsubscribe("true");

        consumerFalse.close();
        session.unsubscribe("false");

        session.close();
        con.close();

        PersistenceAdapter persistenceAdapter = broker.getPersistenceAdapter();
        if( persistenceAdapter instanceof KahaDBStore) {
            final KahaDBStore store = ((KahaDBPersistenceAdapter) persistenceAdapter).getStore();
            LOG.info("Store page count: " + store.getPageFile().getPageCount());
            LOG.info("Store free page count: " + store.getPageFile().getFreePageCount());
            LOG.info("Store page in-use: " + (store.getPageFile().getPageCount() - store.getPageFile().getFreePageCount()));

            assertTrue("no leak of pages, always use just 10", Wait.waitFor(new Wait.Condition() {
                @Override
                public boolean isSatisified() throws Exception {
                    return 10 == store.getPageFile().getPageCount() -
                            store.getPageFile().getFreePageCount();
                }
            }, TimeUnit.SECONDS.toMillis(10)));
        }
    }

};