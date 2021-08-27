//,temp,JmsSchedulerTest.java,54,94,temp,JmsCronSchedulerTest.java,49,97
//,3
public class xxx {
    @Test
    public void testSimulatenousCron() throws Exception {

        final int COUNT = 10;
        final AtomicInteger count = new AtomicInteger();
        Connection connection = createConnection();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        MessageConsumer consumer = session.createConsumer(destination);

        final CountDownLatch latch = new CountDownLatch(COUNT);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                count.incrementAndGet();
                latch.countDown();
                assertTrue(message instanceof TextMessage);
                TextMessage tm = (TextMessage) message;
                try {
                    LOG.info("Received [{}] count: {} ", tm.getText(), count.get());
                } catch (JMSException e) {
                    LOG.error("Unexpected exception in onMessage", e);
                    fail("Unexpected exception in onMessage: " + e.getMessage());
                }
            }
        });

        connection.start();
        for (int i = 0; i < COUNT; i++) {
            MessageProducer producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage("test msg "+ i);
            message.setStringProperty(ScheduledMessage.AMQ_SCHEDULED_CRON, "* * * * *");
            producer.send(message);
            LOG.info("Message {} sent at {}", i, new Date().toString());
            producer.close();
            // wait a couple sec so cron start time is different for next message
            Thread.sleep(2000);
        }
        SchedulerBroker sb = (SchedulerBroker) this.broker.getBroker().getAdaptor(SchedulerBroker.class);
        JobScheduler js = sb.getJobScheduler();
        List<Job> list = js.getAllJobs();
        assertEquals(COUNT, list.size());
        latch.await(2, TimeUnit.MINUTES);
        // All should messages should have been received by now
        assertEquals(COUNT, count.get());

        connection.close();
    }

};