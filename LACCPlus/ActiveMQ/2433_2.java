//,temp,DurableSubscriptionOfflineTest.java,688,766,temp,DurableSubscriptionOfflineTest.java,604,685
//,3
public class xxx {
    @Test(timeout = 60 * 1000)
    public void testAllConsumed() throws Exception {
        final String filter = "filter = 'true'";
        Connection con = createConnection("cli1");
        Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        session.createDurableSubscriber(topic, "SubsId", filter, true);
        session.close();
        con.close();

        con = createConnection("cli2");
        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        session.createDurableSubscriber(topic, "SubsId", filter, true);
        session.close();
        con.close();

        // send messages
        con = createConnection();
        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(null);

        int sent = 0;
        for (int i = 0; i < 10; i++) {
            Message message = session.createMessage();
            message.setStringProperty("filter", "true");
            producer.send(topic, message);
            sent++;
        }

        LOG.info("sent: " + sent);
        Thread.sleep(1 * 1000);
        session.close();
        con.close();

        con = createConnection("cli1");
        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createDurableSubscriber(topic, "SubsId", filter, true);
        DurableSubscriptionOfflineTestListener listener = new DurableSubscriptionOfflineTestListener();
        consumer.setMessageListener(listener);
        Thread.sleep(3 * 1000);
        session.close();
        con.close();

        assertEquals(sent, listener.count);

        LOG.info("cli2 pull 2");
        con = createConnection("cli2");
        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        consumer = session.createDurableSubscriber(topic, "SubsId", filter, true);
        assertNotNull("got message", consumer.receive(2000));
        assertNotNull("got message", consumer.receive(2000));
        session.close();
        con.close();

        // send messages
        con = createConnection();
        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        producer = session.createProducer(null);

        sent = 0;
        for (int i = 0; i < 2; i++) {
            Message message = session.createMessage();
            message.setStringProperty("filter", i==1 ? "true" : "false");
            producer.send(topic, message);
            sent++;
        }
        LOG.info("sent: " + sent);
        Thread.sleep(1 * 1000);
        session.close();
        con.close();

        LOG.info("cli1 again, should get 1 new ones");
        con = createConnection("cli1");
        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        consumer = session.createDurableSubscriber(topic, "SubsId", filter, true);
        listener = new DurableSubscriptionOfflineTestListener();
        consumer.setMessageListener(listener);
        Thread.sleep(3 * 1000);
        session.close();
        con.close();

        assertEquals(1, listener.count);
    }

};