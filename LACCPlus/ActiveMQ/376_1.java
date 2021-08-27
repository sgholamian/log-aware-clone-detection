//,temp,JMSClientProducerFlowSendFailIfNoSpace.java,107,147,temp,ProducerFlowControlTest.java,136,182
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testPubisherRecoverAfterBlockWithSyncSend() throws Exception {
        connection = createConnection(false, false);

        final Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        final Queue queueA = session.createQueue(name.getMethodName());
        final MessageProducer producer = session.createProducer(queueA);

        final AtomicBoolean keepGoing = new AtomicBoolean(true);
        final AtomicInteger exceptionCount = new AtomicInteger(0);
        Thread thread = new Thread("Filler") {
            @Override
            public void run() {
                while (keepGoing.get()) {
                    try {
                        producer.send(session.createTextMessage("Test message"));
                    } catch (JMSException jmsEx) {
                        LOG.debug("Client caught error: {} {}", jmsEx.getClass().getName(), jmsEx.getMessage());
                        gotResourceException.set(true);
                        exceptionCount.incrementAndGet();
                    }
                }
            }
        };
        thread.start();
        waitForBlockedOrResourceLimit();

        // resourceException on second message, resumption if we
        // can receive 10
        MessageConsumer consumer = session.createConsumer(queueA);
        TextMessage msg;
        for (int idx = 0; idx < 10; ++idx) {
            msg = (TextMessage) consumer.receive(500);
            if (msg != null) {
                msg.acknowledge();
            }
        }

        assertTrue("we were blocked at least 5 times", 5 < exceptionCount.get());
        keepGoing.set(false);
    }

};