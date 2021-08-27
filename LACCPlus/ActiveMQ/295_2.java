//,temp,AMQ4487Test.java,104,134,temp,JmsQueueBrowserTest.java,303,337
//,3
public class xxx {
    public void testLargeNumberOfMessages() throws Exception {
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        ActiveMQQueue destination = new ActiveMQQueue("TEST");
        connection.start();

        MessageProducer producer = session.createProducer(destination);

        int numberOfMessages = 4096;

        for (int i = 0; i < numberOfMessages; i++) {
            producer.send(session.createTextMessage("Message: "  + i));
        }

        QueueBrowser browser = session.createBrowser(destination);
        Enumeration<?> enumeration = browser.getEnumeration();

        assertTrue(enumeration.hasMoreElements());

        int numberBrowsed = 0;

        while (enumeration.hasMoreElements()) {
            Message browsed = (Message) enumeration.nextElement();

            if (LOG.isDebugEnabled()) {
                LOG.debug("Browsed Message [{}]", browsed.getJMSMessageID());
            }

            numberBrowsed++;
        }

        System.out.println("Number browsed:  " + numberBrowsed);
        assertEquals(numberOfMessages, numberBrowsed);
        browser.close();
        producer.close();
    }

};