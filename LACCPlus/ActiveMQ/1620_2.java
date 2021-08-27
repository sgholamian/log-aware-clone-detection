//,temp,QueueBrowsingTest.java,183,219,temp,QueueBrowsingTest.java,83,115
//,3
public class xxx {
    @Test
    public void testBrowsing() throws JMSException {

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

        QueueBrowser browser = session.createBrowser(queue);
        Enumeration<?> enumeration = browser.getEnumeration();
        int received = 0;
        while (enumeration.hasMoreElements()) {
            Message m = (Message) enumeration.nextElement();
            received++;
            LOG.info("Browsed message " + received + ": " + m.getJMSMessageID());
        }

        browser.close();

        assertEquals(messageToSend, received);
    }

};