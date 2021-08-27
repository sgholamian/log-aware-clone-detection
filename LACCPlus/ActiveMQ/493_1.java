//,temp,ExpiredMessagesWithNoConsumerTest.java,377,518,temp,ExpiredMessagesWithNoConsumerTest.java,258,375
//,3
public class xxx {
    public void testExpiredMessagesWithVerySlowConsumerCanContinue() throws Exception {
        createBroker();
        final long queuePrefetch = 600;
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
                connectionUri + "?jms.prefetchPolicy.queuePrefetch=" + queuePrefetch);
        connection = factory.createConnection();
        session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        producer = session.createProducer(destination);
        final int ttl = 4000;
        producer.setTimeToLive(ttl);

        final long sendCount = 1500;
        final CountDownLatch receivedOneCondition = new CountDownLatch(1);
        final CountDownLatch waitCondition = new CountDownLatch(1);
        final AtomicLong received = new AtomicLong();
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                try {
                    if(LOG.isDebugEnabled()) {
                        LOG.debug("Got my message: " + message);
                    }
                    receivedOneCondition.countDown();
                    received.incrementAndGet();
                    waitCondition.await(5, TimeUnit.MINUTES);
                    if(LOG.isDebugEnabled()) {
                        LOG.debug("acking message: " + message);
                    }
                    message.acknowledge();
                } catch (Exception e) {
                    e.printStackTrace();
                    fail(e.toString());
                }
            }
        });

        connection.start();

        final Thread producingThread = new Thread("Producing Thread") {
            @Override
            public void run() {
                try {
                    int i = 0;
                    long tStamp = System.currentTimeMillis();
                    while (i++ < sendCount) {
                        producer.send(session.createTextMessage("test"));
                        if (i%100 == 0) {
                            LOG.info("sent: " + i + " @ " + ((System.currentTimeMillis() - tStamp) / 100)  + "m/ms");
                            tStamp = System.currentTimeMillis() ;
                        }
                    }
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        };

        producingThread.start();
        assertTrue("got one message", receivedOneCondition.await(20, TimeUnit.SECONDS));

        assertTrue("producer failed to complete within allocated time", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                producingThread.join(1000);
                return !producingThread.isAlive();
            }
        }, Wait.MAX_WAIT_MILLIS * 10));

        final DestinationViewMBean view = createView(destination);

        assertTrue("Not all dispatched up to default prefetch ", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return queuePrefetch == view.getDispatchCount();
            }
        }));

        assertTrue("all non inflight have expired ", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("enqueue=" + view.getEnqueueCount() + ", dequeue=" + view.getDequeueCount()
                        + ", inflight=" + view.getInFlightCount() + ", expired= " + view.getExpiredCount()
                        + ", size= " + view.getQueueSize());

                return view.getExpiredCount() > 0 && (view.getEnqueueCount() - view.getInFlightCount()) == view.getExpiredCount();
            }
        }));

        LOG.info("enqueue=" + view.getEnqueueCount() + ", dequeue=" + view.getDequeueCount()
                + ", inflight=" + view.getInFlightCount() + ", expired= " + view.getExpiredCount()
                + ", size= " + view.getQueueSize());

        // let the ack happen
        waitCondition.countDown();

        Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return 0 == view.getInFlightCount();
            }
        });
        LOG.info("enqueue=" + view.getEnqueueCount() + ", dequeue=" + view.getDequeueCount()
                + ", inflight=" + view.getInFlightCount() + ", expired= " + view.getExpiredCount()
                + ", size= " + view.getQueueSize());

        assertEquals("inflight didn't reduce to duck",
                     0, view.getInFlightCount());
        assertEquals("size doesn't get back to 0 ", 0, view.getQueueSize());
        assertEquals("dequeues don't match sent/expired ", sendCount, view.getDequeueCount());

        // produce some more
        producer.setTimeToLive(0);
        long tStamp = System.currentTimeMillis();
        for (int i=0; i<sendCount; i++) {
            producer.send(session.createTextMessage("test-" + i));
            if (i%100 == 0) {
                LOG.info("sent: " + i + " @ " + ((System.currentTimeMillis() - tStamp) / 100)  + "m/ms");
                tStamp = System.currentTimeMillis() ;
            }
        }

        Wait.waitFor(new Wait.Condition() {
             @Override
            public boolean isSatisified() throws Exception {
                 return received.get() >= sendCount;
             }
         });

        consumer.close();

        Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return 0 == view.getInFlightCount();
            }
        });
        assertEquals("inflight did not go to zero on close", 0, view.getInFlightCount());

        LOG.info("done: " + getName());
    }

};