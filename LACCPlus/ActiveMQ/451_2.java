//,temp,DurableSubscriptionOffline1Test.java,115,170,temp,DurableSubscriptionOffline3Test.java,329,390
//,3
public class xxx {
    @Test(timeout = 60 * 1000)
    public void testOfflineSubscriptionAfterRestart() throws Exception {
        // create offline subs 1
        Connection con = createConnection("offCli1");
        Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createDurableSubscriber(topic, "SubsId", null, false);
        DurableSubscriptionOfflineTestListener listener = new DurableSubscriptionOfflineTestListener();
        consumer.setMessageListener(listener);

        // send messages
        MessageProducer producer = session.createProducer(null);

        int sent = 0;
        for (int i = 0; i < 10; i++) {
            sent++;
            Message message = session.createMessage();
            message.setStringProperty("filter", "false");
            producer.send(topic, message);
        }

        LOG.info("sent: " + sent);
        Thread.sleep(5 * 1000);
        session.close();
        con.close();

        assertEquals(sent, listener.count);

        // restart broker
        Thread.sleep(3 * 1000);
        broker.stop();
        createBroker(false /*deleteAllMessages*/);

        // send more messages
        con = createConnection();
        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        producer = session.createProducer(null);

        for (int i = 0; i < 10; i++) {
            sent++;
            Message message = session.createMessage();
            message.setStringProperty("filter", "false");
            producer.send(topic, message);
        }

        LOG.info("after restart, sent: " + sent);
        Thread.sleep(1 * 1000);
        session.close();
        con.close();

        // test offline subs
        con = createConnection("offCli1");
        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        consumer = session.createDurableSubscriber(topic, "SubsId", null, false);
        consumer.setMessageListener(listener);

        Thread.sleep(3 * 1000);

        session.close();
        con.close();

        assertEquals(sent, listener.count);
    }

};