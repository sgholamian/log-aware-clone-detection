//,temp,JMSQueueBrowserTest.java,291,320,temp,JMSQueueBrowserTest.java,246,289
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testBrowseAllInQueueSmallPrefetch() throws Exception {
        JmsConnectionFactory cf = new JmsConnectionFactory(getAmqpURI("jms.prefetchPolicy.all=5"));

        connection = cf.createConnection();
        connection.start();

        final int MSG_COUNT = 30;

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        assertNotNull(session);
        Queue queue = session.createQueue(getDestinationName());
        sendMessages(name.getMethodName(), MSG_COUNT, false);

        QueueViewMBean proxy = getProxyToQueue(getDestinationName());
        assertEquals(MSG_COUNT, proxy.getQueueSize());

        QueueBrowser browser = session.createBrowser(queue);
        assertNotNull(browser);
        Enumeration<?> enumeration = browser.getEnumeration();
        int count = 0;
        while (enumeration.hasMoreElements()) {
            Message msg = (Message) enumeration.nextElement();
            assertNotNull(msg);
            LOG.debug("Recv: {}", msg);
            count++;
        }
        assertFalse(enumeration.hasMoreElements());
        assertEquals(MSG_COUNT, count);
    }

};