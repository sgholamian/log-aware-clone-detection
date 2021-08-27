//,temp,JobSchedulerManagementTest.java,183,242,temp,JobSchedulerManagementTest.java,115,181
//,3
public class xxx {
    @Test
    public void testRemoveAllScheduledAtTime() throws Exception {
        final int COUNT = 3;
        Connection connection = createConnection();

        // Setup the scheduled Message
        scheduleMessage(connection, TimeUnit.SECONDS.toMillis(6));
        scheduleMessage(connection, TimeUnit.SECONDS.toMillis(15));
        scheduleMessage(connection, TimeUnit.SECONDS.toMillis(20));

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the Browse Destination and the Reply To location
        Destination management = session.createTopic(ScheduledMessage.AMQ_SCHEDULER_MANAGEMENT_DESTINATION);
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

        long start = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(10);
        long end = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(30);

        // Send the remove request
        MessageProducer producer = session.createProducer(management);
        Message request = session.createMessage();
        request.setStringProperty(ScheduledMessage.AMQ_SCHEDULER_ACTION, ScheduledMessage.AMQ_SCHEDULER_ACTION_REMOVEALL);
        request.setStringProperty(ScheduledMessage.AMQ_SCHEDULER_ACTION_START_TIME, Long.toString(start));
        request.setStringProperty(ScheduledMessage.AMQ_SCHEDULER_ACTION_END_TIME, Long.toString(end));
        producer.send(request);

        // Send the browse request
        request = session.createMessage();
        request.setStringProperty(ScheduledMessage.AMQ_SCHEDULER_ACTION, ScheduledMessage.AMQ_SCHEDULER_ACTION_BROWSE);
        request.setJMSReplyTo(browseDest);
        producer.send(request);

        // now see if we got back only the one remaining message.
        latch.await(10, TimeUnit.SECONDS);
        assertEquals(2, browsedLatch.getCount());

        // Now wait and see if any get delivered, none should.
        latch.await(10, TimeUnit.SECONDS);
        assertEquals(2, latch.getCount());

        connection.close();
    }

};