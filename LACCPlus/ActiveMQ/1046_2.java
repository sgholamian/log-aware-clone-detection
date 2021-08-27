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

        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        session.createDurableSubscriber(topic, "all", null, true);
        session.close();

        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        session.createDurableSubscriber(topic, "all2", null, true);
        session.close();

        con.close();

        // send messages

        final CountDownLatch goOn = new CountDownLatch(1);
        Thread sendThread = new Thread() {
            @Override
            public void run() {
                try {

                    final Connection sendCon = createConnection("send");
                    final Session sendSession = sendCon.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    final MessageProducer producer = sendSession.createProducer(null);

                    for (int i = 0; i < messageCount; i++) {
                        boolean filter = i % 2 == 1;
                        Message message = sendSession.createMessage();
                        message.setStringProperty("filter", filter ? "true" : "false");
                        producer.send(topic, message);

                        if (i > 0 && i % 10000 == 0) {
                            LOG.info("Sent:" + i);
                        }
                        if (i> messageCount/2) {
                            goOn.countDown();
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

        goOn.await(5, TimeUnit.MINUTES);
        LOG.info("Activating consumers");

        // consume messages in parallel
        con = createConnection();
        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);

        MessageConsumer consumerTrue = session.createDurableSubscriber(topic, "true", "filter = 'true'", true);
        Listener listenerT = new Listener();
        consumerTrue.setMessageListener(listenerT);

        MessageConsumer consumerFalse = session.createDurableSubscriber(topic, "false", "filter = 'false'", true);
        Listener listenerF = new Listener();
        consumerFalse.setMessageListener(listenerF);

        MessageConsumer consumerAll = session.createDurableSubscriber(topic, "all", null, true);
        Listener listenerA = new Listener();
        consumerAll.setMessageListener(listenerA);

        MessageConsumer consumerAll2 = session.createDurableSubscriber(topic, "all2", null, true);
        Listener listenerA2 = new Listener();
        consumerAll2.setMessageListener(listenerA2);

        waitFor(listenerA, messageCount);
        assertEquals(messageCount, listenerA.count);

        waitFor(listenerA2, messageCount);
        assertEquals(messageCount, listenerA2.count);

        assertEquals(messageCount / 2, listenerT.count);
        assertEquals(messageCount / 2, listenerF.count);

        consumerTrue.close();
        session.unsubscribe("true");

        consumerFalse.close();
        session.unsubscribe("false");

        consumerAll.close();
        session.unsubscribe("all");

        session.close();
        con.close();

        PersistenceAdapter persistenceAdapter = broker.getPersistenceAdapter();
        if( persistenceAdapter instanceof KahaDBPersistenceAdapter) {
            final KahaDBStore store = ((KahaDBPersistenceAdapter) persistenceAdapter).getStore();
            LOG.info("Store page count: " + store.getPageFile().getPageCount());
            LOG.info("Store free page count: " + store.getPageFile().getFreePageCount());
            LOG.info("Store page in-use: " + (store.getPageFile().getPageCount() - store.getPageFile().getFreePageCount()));

            assertTrue("no leak of pages, always use just 11", Wait.waitFor(new Wait.Condition() {
                @Override
                public boolean isSatisified() throws Exception {
                    return 12 == store.getPageFile().getPageCount() -
                            store.getPageFile().getFreePageCount();
                }
            }, TimeUnit.SECONDS.toMillis(10)));
        }
    }

};