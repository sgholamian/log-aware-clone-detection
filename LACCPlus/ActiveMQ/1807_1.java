//,temp,JmsSchedulerTest.java,54,94,temp,JmsCronSchedulerTest.java,49,97
//,3
public class xxx {
    @Test
    public void testCron() throws Exception {
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
                LOG.info("Received scheduled message, waiting for {} more", latch.getCount());
            }
        });

        connection.start();
        MessageProducer producer = session.createProducer(destination);
        TextMessage message = session.createTextMessage("test msg");
        long time = 1000;
        message.setStringProperty(ScheduledMessage.AMQ_SCHEDULED_CRON, "* * * * *");
        message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, time);
        message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, 500);
        message.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, COUNT - 1);

        producer.send(message);
        producer.close();

        Thread.sleep(500);
        SchedulerBroker sb = (SchedulerBroker) this.broker.getBroker().getAdaptor(SchedulerBroker.class);
        JobScheduler js = sb.getJobScheduler();
        List<Job> list = js.getAllJobs();
        assertEquals(1, list.size());
        latch.await(240, TimeUnit.SECONDS);
        assertEquals(COUNT, count.get());
        connection.close();
    }

};