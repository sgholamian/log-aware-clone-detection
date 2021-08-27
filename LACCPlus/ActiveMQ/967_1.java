//,temp,JMSQueueBrowserTest.java,127,154,temp,JMSInteroperabilityTest.java,410,447
//,3
public class xxx {
    @Test(timeout=30000)
    public void testBroseOneInQueue() throws Exception {
        JmsConnectionFactory cf = new JmsConnectionFactory(getAmqpURI());

        connection = cf.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(getDestinationName());
        MessageProducer producer = session.createProducer(queue);
        producer.send(session.createTextMessage("hello"));
        producer.close();

        QueueBrowser browser = session.createBrowser(queue);
        Enumeration<?> enumeration = browser.getEnumeration();
        while (enumeration.hasMoreElements()) {
            Message m = (Message) enumeration.nextElement();
            assertTrue(m instanceof TextMessage);
            LOG.debug("Browsed message {} from Queue {}", m, queue);
        }

        browser.close();

        MessageConsumer consumer = session.createConsumer(queue);
        Message msg = consumer.receive(5000);
        assertNotNull(msg);
        assertTrue(msg instanceof TextMessage);
    }

};