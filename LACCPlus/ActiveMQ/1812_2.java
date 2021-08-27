//,temp,AMQ2314Test.java,67,149,temp,TempStorageBlockedBrokerTest.java,65,168
//,3
public class xxx {
    public void testRunProducerWithHungConsumer() throws Exception {

        final long origTempUsage = broker.getSystemUsage().getTempUsage().getUsage();

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(connectionUri);
        // ensure messages are spooled to disk for this consumer
        ActiveMQPrefetchPolicy prefetch = new ActiveMQPrefetchPolicy();
        prefetch.setTopicPrefetch(10);
        factory.setPrefetchPolicy(prefetch);
        Connection consumerConnection = factory.createConnection();
        consumerConnection.start();

        Session consumerSession = consumerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = consumerSession.createConsumer(destination);

        final Connection producerConnection = factory.createConnection();
        producerConnection.start();

        final CountDownLatch producerHasSentTenMessages = new CountDownLatch(10);
        Thread producingThread = new Thread("Producing thread") {
            @Override
            public void run() {
                try {
                    Session session = producerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    MessageProducer producer = session.createProducer(destination);
                    producer.setDeliveryMode(deliveryMode);
                    for (int idx = 0; idx < MESSAGES_COUNT; ++idx) {
                        Message message = session.createTextMessage(new String(buf) + idx);

                        producer.send(message);
                        messagesSent.incrementAndGet();
                        producerHasSentTenMessages.countDown();
                        Thread.sleep(10);
                        if (idx != 0 && idx%100 == 0) {
                            LOG.info("Sent Message " + idx);
                            LOG.info("Temp Store Usage " + broker.getSystemUsage().getTempUsage().getUsage());
                        }
                    }
                    producer.close();
                    session.close();
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        };
        producingThread.start();

        assertTrue("producer has sent 10 in a reasonable time", producerHasSentTenMessages.await(30, TimeUnit.SECONDS));

        int count = 0;

        Message m = null;
        while ((m = consumer.receive(messageReceiveTimeout)) != null) {
            count++;
            if (count != 0 && count%10 == 0) {
                LOG.info("Recieved Message (" + count + "):" + m);
            }
            messagesConsumed.incrementAndGet();
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                LOG.info("error sleeping");
            }
        }

        LOG.info("Connection Timeout: Retrying.. count: " + count);

        while ((m = consumer.receive(messageReceiveTimeout)) != null) {
            count++;
            if (count != 0 && count%100 == 0) {
                LOG.info("Recieved Message (" + count + "):" + m);
            }
            messagesConsumed.incrementAndGet();
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                LOG.info("error sleeping");
            }
        }

        LOG.info("consumer session closing: consumed count: " + count);

        consumerSession.close();

        producingThread.join();

        final long tempUsageBySubscription = broker.getSystemUsage().getTempUsage().getUsage();
        LOG.info("Orig Usage: " + origTempUsage + ", currentUsage: " + tempUsageBySubscription);

        producerConnection.close();
        consumerConnection.close();

        LOG.info("Subscrition Usage: " + tempUsageBySubscription + ", endUsage: "
                + broker.getSystemUsage().getTempUsage().getUsage());

        // do a cleanup
        ((PListStoreImpl)broker.getTempDataStore()).run();
        LOG.info("Subscrition Usage: " + tempUsageBySubscription + ", endUsage: "
                        + broker.getSystemUsage().getTempUsage().getUsage());

        assertEquals("Incorrect number of Messages Sent: " + messagesSent.get(), messagesSent.get(), MESSAGES_COUNT);
        assertEquals("Incorrect number of Messages Consumed: " + messagesConsumed.get(), messagesConsumed.get(),
                MESSAGES_COUNT);
    }

};