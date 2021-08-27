//,temp,JMSQueueBrowserTest.java,187,215,temp,JMSQueueBrowserTest.java,52,84
//,3
public class xxx {
    @Test(timeout = 60000)
    @Repeat(repetitions = 5)
    public void testBrowseAllInQueueZeroPrefetch() throws Exception {

        final int MSG_COUNT = 5;

        JmsConnectionFactory cf = new JmsConnectionFactory(getAmqpURI("jms.prefetchPolicy.all=0"));
        connection = cf.createConnection();
        connection.start();

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
        while (count < MSG_COUNT && enumeration.hasMoreElements()) {
            Message msg = (Message) enumeration.nextElement();
            assertNotNull(msg);
            LOG.debug("Recv: {}", msg);
            count++;
        }

        LOG.debug("Received all expected message, checking that hasMoreElements returns false");
        assertFalse(enumeration.hasMoreElements());
        assertEquals(5, count);
    }

};