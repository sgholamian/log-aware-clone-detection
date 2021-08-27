//,temp,DurableSubscriptionOfflineTest.java,688,766,temp,DurableSubscriptionOfflineTest.java,604,685
//,3
public class xxx {
    @Test(timeout = 60 * 1000)
    public void testNoMissOnMatchingSubAfterRestart() throws Exception {

        final String filter = "filter = 'true'";
        Connection con = createConnection("cli1");
        Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        session.createDurableSubscriber(topic, "SubsId", filter, true);
        session.close();
        con.close();

        // send unmatched messages
        con = createConnection();
        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(null);

        int sent = 0;
        // message for cli1 to keep it interested
        Message message = session.createMessage();
        message.setStringProperty("filter", "true");
        message.setIntProperty("ID", 0);
        producer.send(topic, message);
        sent++;

        for (int i = sent; i < 10; i++) {
            message = session.createMessage();
            message.setStringProperty("filter", "false");
            message.setIntProperty("ID", i);
            producer.send(topic, message);
            sent++;
        }
        con.close();
        LOG.info("sent: " + sent);

        // new sub at id 10
        con = createConnection("cli2");
        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        session.createDurableSubscriber(topic, "SubsId", filter, true);
        session.close();
        con.close();

        destroyBroker();
        createBroker(false);

        con = createConnection();
        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        producer = session.createProducer(null);

        for (int i = sent; i < 30; i++) {
            message = session.createMessage();
            message.setStringProperty("filter", "true");
            message.setIntProperty("ID", i);
            producer.send(topic, message);
            sent++;
        }
        con.close();
        LOG.info("sent: " + sent);

        // pick up the first of the next twenty messages
        con = createConnection("cli2");
        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createDurableSubscriber(topic, "SubsId", filter, true);
        Message m = consumer.receive(3000);
        assertEquals("is message 10", 10, m.getIntProperty("ID"));

        session.close();
        con.close();

        // pick up the first few messages for client1
        con = createConnection("cli1");
        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        consumer = session.createDurableSubscriber(topic, "SubsId", filter, true);
        m = consumer.receive(3000);
        assertEquals("is message 0", 0, m.getIntProperty("ID"));
        m = consumer.receive(3000);
        assertEquals("is message 10", 10, m.getIntProperty("ID"));

        session.close();
        con.close();
    }

};