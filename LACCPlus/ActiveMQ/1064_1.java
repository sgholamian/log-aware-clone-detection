//,temp,StompAdvisoryTest.java,346,391,temp,StompAdvisoryTest.java,272,307
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testStatisticsAdvisory() throws Exception {
        Connection c = cf.createConnection("system", "manager");
        c.start();
        final Session session = c.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final Topic replyTo = session.createTopic("stats");

        // Dummy Queue used to later gather statistics.
        final ActiveMQQueue testQueue = new ActiveMQQueue("queueToBeTestedForStats");
        final MessageProducer producer = session.createProducer(null);
        Message mess = session.createTextMessage("test");
        producer.send(testQueue, mess);

        // Create a request for Queue statistics
        Thread child = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    Queue query = session.createQueue(STATS_DESTINATION_PREFIX + testQueue.getQueueName());
                    Message msg = session.createMessage();
                    msg.setJMSReplyTo(replyTo);
                    producer.send(query, msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        child.start();

        stompConnect();
        // Attempt to gather the statistics response from the previous request.
        stompConnection.connect("system", "manager");
        stompConnection.subscribe("/topic/" + replyTo.getTopicName(), Stomp.Headers.Subscribe.AckModeValues.AUTO);
        stompConnection.begin("TX");
        StompFrame f = stompConnection.receive(5000);
        stompConnection.commit("TX");

        LOG.debug(f.toString());
        assertEquals(f.getAction(),"MESSAGE");
        assertTrue("Should have a body", f.getBody().length() > 0);
        assertTrue("Should contains memoryUsage stats", f.getBody().contains("memoryUsage"));

        c.stop();
        c.close();
    }

};