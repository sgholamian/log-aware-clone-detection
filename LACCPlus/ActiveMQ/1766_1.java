//,temp,DurableSubscriptionOffline4Test.java,63,129,temp,DurableSubscriptionOffline3Test.java,243,327
//,3
public class xxx {
    @Test(timeout = 60 * 1000)
    // https://issues.apache.org/jira/browse/AMQ-3206
    public void testCleanupDeletedSubAfterRestart() throws Exception {
        Connection con = createConnection("cli1");
        Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        session.createDurableSubscriber(topic, "SubsId", null, true);
        session.close();
        con.close();

        con = createConnection("cli2");
        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        session.createDurableSubscriber(topic, "SubsId", null, true);
        session.close();
        con.close();

        con = createConnection();
        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(null);

        final int toSend = 500;
        final String payload = new byte[40*1024].toString();
        int sent = 0;
        for (int i = sent; i < toSend; i++) {
            Message message = session.createTextMessage(payload);
            message.setStringProperty("filter", "false");
            message.setIntProperty("ID", i);
            producer.send(topic, message);
            sent++;
        }
        con.close();
        LOG.info("sent: " + sent);

        // kill off cli1
        con = createConnection("cli1");
        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        session.unsubscribe("SubsId");

        destroyBroker();
        createBroker(false);

        con = createConnection("cli2");
        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createDurableSubscriber(topic, "SubsId", null, true);
        final DurableSubscriptionOfflineTestListener listener = new DurableSubscriptionOfflineTestListener();
        consumer.setMessageListener(listener);
        assertTrue("got all sent", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("Want: " + toSend  + ", current: " + listener.count);
                return listener.count == toSend;
            }
        }));
        session.close();
        con.close();

        destroyBroker();
        createBroker(false);
        final KahaDBPersistenceAdapter pa = (KahaDBPersistenceAdapter) broker.getPersistenceAdapter();
        assertTrue("Should have less than three journal files left but was: " +
                pa.getStore().getJournal().getFileMap().size(), Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return pa.getStore().getJournal().getFileMap().size() <= 3;
            }
        }));
    }

};