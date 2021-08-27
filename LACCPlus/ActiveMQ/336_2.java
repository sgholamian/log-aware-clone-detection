//,temp,JMSQueueBrowserTest.java,217,244,temp,JMSQueueBrowserTest.java,156,185
//,3
public class xxx {
    @Test(timeout = 60000)
    @Repeat(repetitions = 5)
    public void testBrowseAllInQueue() throws Exception {
        JmsConnectionFactory cf = new JmsConnectionFactory(getAmqpURI());

        connection = cf.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        assertNotNull(session);
        Queue queue = session.createQueue(getDestinationName());
        sendMessages(name.getMethodName(), 5, false);

        QueueViewMBean proxy = getProxyToQueue(getDestinationName());
        assertEquals(5, proxy.getQueueSize());

        QueueBrowser browser = session.createBrowser(queue);
        assertNotNull(browser);
        Enumeration<?> enumeration = browser.getEnumeration();
        int count = 0;
        while (enumeration.hasMoreElements()) {
            Message msg = (Message) enumeration.nextElement();
            assertNotNull(msg);
            LOG.debug("Recv: {}", msg);
            count++;
            TimeUnit.MILLISECONDS.sleep(50);
        }
        assertFalse(enumeration.hasMoreElements());
        assertEquals(5, count);
    }

};