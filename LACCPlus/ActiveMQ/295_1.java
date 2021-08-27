//,temp,AMQ4487Test.java,104,134,temp,JmsQueueBrowserTest.java,303,337
//,3
public class xxx {
    @SuppressWarnings("rawtypes")
    private void doTestBrowsing(int messagesToSend) throws Exception {

        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(destinationName);

        sendMessages(messagesToSend);

        QueueBrowser browser = session.createBrowser(queue);
        Enumeration enumeration = browser.getEnumeration();
        int received = 0;
        while (enumeration.hasMoreElements()) {
            Message m = (Message) enumeration.nextElement();
            assertNotNull(m);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Browsed Message: {}", m.getJMSMessageID());
            }

            received++;
            if (received > messagesToSend) {
                break;
            }
        }

        browser.close();

        assertEquals(messagesToSend, received);
    }

};