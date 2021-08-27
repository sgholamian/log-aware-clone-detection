//,temp,QueueBrowsingTest.java,183,219,temp,QueueBrowsingTest.java,83,115
//,3
public class xxx {
    @Test
    public void testMemoryLimit() throws Exception {
        broker.getSystemUsage().getMemoryUsage().setLimit((maxPageSize + 10) * 4 * 1024);

        int messageToSend = 370;

        ActiveMQQueue queue = new ActiveMQQueue("TEST");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(queue);

        String data = "";
        for( int i=0; i < 1024*2; i++ ) {
            data += "x";
        }

        for( int i=0; i < messageToSend; i++ ) {
            producer.send(session.createTextMessage(data));
        }

        //Consume one message to free memory and allow the cursor to pageIn messages
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.receive(1000);

        QueueBrowser browser = session.createBrowser(queue);
        Enumeration<?> enumeration = browser.getEnumeration();
        int received = 0;
        while (enumeration.hasMoreElements()) {
            Message m = (Message) enumeration.nextElement();
            received++;
            LOG.info("Browsed message " + received + ": " + m.getJMSMessageID());
        }

        browser.close();
        assertTrue("got at least maxPageSize", received >= maxPageSize);
    }

};