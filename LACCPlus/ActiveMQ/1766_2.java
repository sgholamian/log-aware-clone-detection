//,temp,DurableSubscriptionOffline4Test.java,63,129,temp,DurableSubscriptionOffline3Test.java,243,327
//,3
public class xxx {
    @Test(timeout = 60 * 1000)
    public void testOfflineSubscriptionWithSelectorAfterRestart() throws Exception {
        // create offline subs 1
        Connection con = createConnection("offCli1");
        Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        session.createDurableSubscriber(topic, "SubsId", "filter = 'true'", true);
        session.close();
        con.close();

        // create offline subs 2
        con = createConnection("offCli2");
        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        session.createDurableSubscriber(topic, "SubsId", "filter = 'true'", true);
        session.close();
        con.close();

        // send messages
        con = createConnection();
        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(null);

        int filtered = 0;
        for (int i = 0; i < 10; i++) {
            boolean filter = (int) (Math.random() * 2) >= 1;
            if (filter)
                filtered++;

            Message message = session.createMessage();
            message.setStringProperty("filter", filter ? "true" : "false");
            producer.send(topic, message);
        }

        LOG.info("sent: " + filtered);
        Thread.sleep(1 * 1000);
        session.close();
        con.close();

        // restart broker
        Thread.sleep(3 * 1000);
        broker.stop();
        createBroker(false /*deleteAllMessages*/);

        // send more messages
        con = createConnection();
        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        producer = session.createProducer(null);

        for (int i = 0; i < 10; i++) {
            boolean filter = (int) (Math.random() * 2) >= 1;
            if (filter)
                filtered++;

            Message message = session.createMessage();
            message.setStringProperty("filter", filter ? "true" : "false");
            producer.send(topic, message);
        }

        LOG.info("after restart, total sent with filter='true': " + filtered);
        Thread.sleep(1 * 1000);
        session.close();
        con.close();

        // test offline subs
        con = createConnection("offCli1");
        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createDurableSubscriber(topic, "SubsId", "filter = 'true'", true);
        DurableSubscriptionOfflineTestListener listener = new DurableSubscriptionOfflineTestListener("1>");
        consumer.setMessageListener(listener);

        Connection con3 = createConnection("offCli2");
        Session session3 = con3.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer3 = session3.createDurableSubscriber(topic, "SubsId", "filter = 'true'", true);
        DurableSubscriptionOfflineTestListener listener3 = new DurableSubscriptionOfflineTestListener();
        consumer3.setMessageListener(listener3);

        Thread.sleep(3 * 1000);

        session.close();
        con.close();
        session3.close();
        con3.close();

        assertEquals(filtered, listener.count);
        assertEquals(filtered, listener3.count);
    }

};