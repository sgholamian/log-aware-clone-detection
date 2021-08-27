//,temp,JmsTimeoutTest.java,95,130,temp,AMQ6240Test.java,41,81
//,3
public class xxx {
    public void testBlockedProducerUsageSendFailTimeout() throws Exception {
        final ActiveMQConnection cx = (ActiveMQConnection)createConnection();
        final ActiveMQDestination queue = createDestination("testqueue");

        broker.getSystemUsage().setSendFailIfNoSpaceAfterTimeout(5000);
        Runnable r = new Runnable() {
            public void run() {
                try {
                    LOG.info("Sender thread starting");
                    Session session = cx.createSession(false, 1);
                    MessageProducer producer = session.createProducer(queue);
                    producer.setDeliveryMode(DeliveryMode.PERSISTENT);

                    TextMessage message = session.createTextMessage(createMessageText());
                    for(int count=0; count<messageCount; count++){
                        producer.send(message);
                    }
                    LOG.info("Done sending..");
                } catch (JMSException e) {
                    if (e instanceof ResourceAllocationException || e.getCause() instanceof RequestTimedOutIOException) {
                        exceptionCount.incrementAndGet();
                    } else {
                        e.printStackTrace();
                    }
                    return;
                }
            }
        };
        cx.start();
        Thread producerThread = new Thread(r);
        producerThread.start();
        producerThread.join(30000);
        cx.close();
        // We should have a few timeout exceptions as memory store will fill up
        assertTrue("No exception from the broker", exceptionCount.get() > 0);
    }

};