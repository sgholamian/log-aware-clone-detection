//,temp,JMSQueueBrowserTest.java,291,320,temp,JMSQueueBrowserTest.java,246,289
//,3
public class xxx {
    @Test(timeout = 40000)
    public void testQueueBrowserInTxSessionLeavesOtherWorkUnaffected() throws Exception {
        JmsConnectionFactory cf = new JmsConnectionFactory(getAmqpURI());

        connection = cf.createConnection();
        connection.start();

        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
        assertNotNull(session);
        Queue queue = session.createQueue(getDestinationName());
        sendMessages(name.getMethodName(), 5, false);

        QueueViewMBean proxy = getProxyToQueue(getDestinationName());
        assertEquals(5, proxy.getQueueSize());

        // Send some TX work but don't commit.
        MessageProducer txProducer = session.createProducer(queue);
        for (int i = 0; i < 5; ++i) {
            txProducer.send(session.createMessage());
        }

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
        }

        assertFalse(enumeration.hasMoreElements());
        assertEquals(5, count);

        browser.close();

        // Now check that all browser work did not affect the session transaction.
        assertEquals(5, proxy.getQueueSize());
        session.commit();
        assertEquals(10, proxy.getQueueSize());
    }

};