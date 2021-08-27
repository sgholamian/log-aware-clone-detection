//,temp,QueueBrowsingLimitTest.java,80,112,temp,JDBCTablePrefixAssignedTest.java,66,92
//,3
public class xxx {
    @Test
    public void testBrowsingLimited() throws Exception {

        int messageToSend = 470;

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

        assertEquals(browserLimit, received);
    }

};