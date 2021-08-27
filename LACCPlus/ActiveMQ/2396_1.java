//,temp,MessagePriorityTest.java,402,459,temp,MessagePriorityTest.java,321,395
//,3
public class xxx {
    public void testHighPriorityDeliveryInterleaved() throws Exception {

        // get zero prefetch
        ActiveMQPrefetchPolicy prefetch = new ActiveMQPrefetchPolicy();
        prefetch.setAll(0);
        factory.setPrefetchPolicy(prefetch);
        conn.close();
        conn = factory.createConnection();
        conn.setClientID("priority");
        conn.start();
        sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

        ActiveMQTopic topic = (ActiveMQTopic)sess.createTopic("TEST");
        final String subName = "priorityDisconnect";
        TopicSubscriber sub = sess.createDurableSubscriber(topic, subName);
        sub.close();

        ProducerThread producerThread = new ProducerThread(topic, 1, HIGH_PRI);
        producerThread.run();

        producerThread.setMessagePriority(HIGH_PRI -1);
        producerThread.setMessageCount(1);
        producerThread.run();

        producerThread.setMessagePriority(LOW_PRI);
        producerThread.setMessageCount(1);
        producerThread.run();
        LOG.info("Ordered priority messages sent");

        sub = sess.createDurableSubscriber(topic, subName);

        Message msg = sub.receive(15000);
        assertNotNull("Message was null", msg);
        LOG.info("received " + msg.getJMSMessageID() + ", priority:" + msg.getJMSPriority());
        assertEquals("Message has wrong priority", HIGH_PRI, msg.getJMSPriority());

        producerThread.setMessagePriority(LOW_PRI+1);
        producerThread.setMessageCount(1);
        producerThread.run();

        msg = sub.receive(15000);
        assertNotNull("Message was null", msg);
        LOG.info("received " + msg.getJMSMessageID() + ", priority:" + msg.getJMSPriority());
        assertEquals("high priority", HIGH_PRI -1, msg.getJMSPriority());

        msg = sub.receive(15000);
        assertNotNull("Message was null", msg);
        LOG.info("received hi? : " + msg);
        assertEquals("high priority", LOW_PRI +1, msg.getJMSPriority());

        msg = sub.receive(15000);
        assertNotNull("Message was null", msg);
        LOG.info("received hi? : " + msg);
        assertEquals("high priority", LOW_PRI, msg.getJMSPriority());

        msg = sub.receive(4000);
        assertNull("Message was null", msg);
    }

};