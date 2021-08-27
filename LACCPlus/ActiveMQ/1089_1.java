//,temp,JobSchedulerManagementTest.java,183,242,temp,JobSchedulerManagementTest.java,115,181
//,3
public class xxx {
    @Test
    public void testBrowseAllScheduled() throws Exception {
        final int COUNT = 10;
        Connection connection = createConnection();

        // Setup the scheduled Message
        scheduleMessage(connection, TimeUnit.SECONDS.toMillis(9), COUNT);

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the Browse Destination and the Reply To location
        Destination requestBrowse = session.createTopic(ScheduledMessage.AMQ_SCHEDULER_MANAGEMENT_DESTINATION);
        Destination browseDest = session.createTemporaryQueue();

        // Create the eventual Consumer to receive the scheduled message
        MessageConsumer consumer = session.createConsumer(destination);

        final CountDownLatch latch = new CountDownLatch(COUNT);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                latch.countDown();
            }
        });

        // Create the "Browser"
        MessageConsumer browser = session.createConsumer(browseDest);
        final CountDownLatch browsedLatch = new CountDownLatch(COUNT);
        browser.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                browsedLatch.countDown();
                LOG.debug("Scheduled Message Browser got Message: " + message);
            }
        });

        connection.start();

        // Send the browse request
        MessageProducer producer = session.createProducer(requestBrowse);
        Message request = session.createMessage();
        request.setStringProperty(ScheduledMessage.AMQ_SCHEDULER_ACTION, ScheduledMessage.AMQ_SCHEDULER_ACTION_BROWSE);
        request.setJMSReplyTo(browseDest);
        producer.send(request);

        // make sure the message isn't delivered early because we browsed it
        Thread.sleep(2000);
        assertEquals(latch.getCount(), COUNT);

        // now see if we got all the scheduled messages on the browse
        // destination.
        latch.await(10, TimeUnit.SECONDS);
        assertEquals(browsedLatch.getCount(), 0);

        // now check that they all got delivered
        latch.await(10, TimeUnit.SECONDS);
        assertEquals(latch.getCount(), 0);

        connection.close();
    }

};