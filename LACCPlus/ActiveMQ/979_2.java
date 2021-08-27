//,temp,JmsTimeoutTest.java,95,130,temp,AMQ6240Test.java,41,81
//,3
public class xxx {
    public void testBlockedTxProducerConnectionTimeoutConnectionCanClose() throws Exception {
        final ActiveMQConnection cx = (ActiveMQConnection)createConnection();
        final ActiveMQDestination queue = createDestination("noPfc");

        cx.setSendTimeout(4000);
        cx.setCloseTimeout(1000);

        final AtomicInteger exceptionCount = new AtomicInteger(0);
        Runnable r = new Runnable() {
            public void run() {
                int count=0;
                try {
                    LOG.info("Sender thread starting");
                    Session session = cx.createSession(true, Session.SESSION_TRANSACTED);
                    MessageProducer producer = session.createProducer(queue);

                    BytesMessage message = session.createBytesMessage();
                    message.writeBytes(new byte[8*1024]);
                    for(; count<100; count++){
                        producer.send(message);
                    }
                    LOG.info("Done sending..");
                } catch (JMSException e) {
                    if (e.getCause() instanceof RequestTimedOutIOException) {
                        exceptionCount.incrementAndGet();
                        LOG.info("Got expected send time out on message: " + count);
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
        producerThread.join(7000);
        cx.close();
        // We should have a few timeout exceptions as store will fill up
        assertTrue("No exception from the broker", exceptionCount.get() > 0);
    }

};