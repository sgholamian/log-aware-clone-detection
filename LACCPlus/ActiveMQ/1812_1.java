//,temp,AMQ2314Test.java,67,149,temp,TempStorageBlockedBrokerTest.java,65,168
//,3
public class xxx {
    public void runProducerWithHungConsumer() throws Exception {

        final CountDownLatch consumerContinue = new CountDownLatch(1);
        final CountDownLatch consumerReady = new CountDownLatch(1);

        final long origTempUsage = broker.getSystemUsage().getTempUsage().getUsage();

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(connectionUri);
        factory.setAlwaysSyncSend(true);

        // ensure messages are spooled to disk for this consumer
        ActiveMQPrefetchPolicy prefetch = new ActiveMQPrefetchPolicy();
        prefetch.setTopicPrefetch(500);
        factory.setPrefetchPolicy(prefetch);
        final Connection connection = factory.createConnection();
        connection.start();

        Thread producingThread = new Thread("Producing thread") {
            public void run() {
                try {
                    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    MessageProducer producer = session.createProducer(destination);
                    producer.setDeliveryMode(deliveryMode);
                    for (int idx = 0; idx < MESSAGES_COUNT; ++idx) {
                        Message message = session.createTextMessage(new String(buf) + idx);
                        producer.send(message);
                    }
                    producer.close();
                    session.close();
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        };

        Thread consumingThread = new Thread("Consuming thread") {
            public void run() {
                try {
                    int count = 0;
                    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    MessageConsumer consumer = session.createConsumer(destination);

                    while (consumer.receive(messageReceiveTimeout) == null) {
                        consumerReady.countDown();
                    }
                    count++;
                    LOG.info("Received one... waiting");
                    consumerContinue.await();
                    if (consumeAll) {
                        LOG.info("Consuming the rest of the messages...");
                        while (consumer.receive(messageReceiveTimeout) != null) {
                            count++;
                        }
                    }
                    LOG.info("consumer session closing: consumed count: " + count);
                    session.close();
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        };
        consumingThread.start();
        consumerReady.await();

        producingThread.start();
        producingThread.join();

        final long tempUsageBySubscription = broker.getSystemUsage().getTempUsage().getUsage();
        LOG.info("Orig Usage: " + origTempUsage + ", currentUsage: " + tempUsageBySubscription);
        assertTrue("some temp store has been used", tempUsageBySubscription != origTempUsage);
        consumerContinue.countDown();
        consumingThread.join();
        connection.close();

        LOG.info("Subscription Usage: " + tempUsageBySubscription + ", endUsage: "
                + broker.getSystemUsage().getTempUsage().getUsage());

        assertTrue("temp usage decreased with removed sub", Wait.waitFor(new Wait.Condition(){
            public boolean isSatisified() throws Exception {
                return broker.getSystemUsage().getTempUsage().getUsage()  < tempUsageBySubscription;
            }
        }));
    }

};