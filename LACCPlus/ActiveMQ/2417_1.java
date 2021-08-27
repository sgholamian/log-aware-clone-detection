//,temp,MessageEvictionTest.java,153,230,temp,MessageEvictionTest.java,99,151
//,3
public class xxx {
    public void doTestMessageEvictionMemoryUsage() throws Exception {

        ExecutorService executor = Executors.newCachedThreadPool();
        final CountDownLatch doAck = new CountDownLatch(1);
        final CountDownLatch ackDone = new CountDownLatch(1);
        final CountDownLatch consumerRegistered = new CountDownLatch(1);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final MessageConsumer consumer = session.createConsumer(destination);
                    consumer.setMessageListener(new MessageListener() {
                        @Override
                        public void onMessage(Message message) {
                            try {
                                // very slow, only ack once
                                doAck.await(60, TimeUnit.SECONDS);
                                LOG.info("acking: " + message.getJMSMessageID());
                                message.acknowledge();
                                ackDone.countDown();
                            } catch (Exception e) {
                                e.printStackTrace();
                                fail(e.toString());
                            } finally {
                                consumerRegistered.countDown();
                                ackDone.countDown();
                            }
                        }
                    });
                    consumerRegistered.countDown();
                    ackDone.await(60, TimeUnit.SECONDS);
                    consumer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    fail(e.toString());
                }
            }
        });

        assertTrue("we have a consumer", consumerRegistered.await(10, TimeUnit.SECONDS));

        final AtomicInteger sent = new AtomicInteger(0);
        final CountDownLatch sendDone = new CountDownLatch(1);
        executor.execute(new Runnable() {
            @Override
            public void run() {
               MessageProducer producer;
               try {
                   producer = session.createProducer(destination);
                   for (int i=0; i< numMessages; i++) {
                       producer.send(session.createTextMessage(payload));
                       sent.incrementAndGet();
                   }
                   producer.close();
                   sendDone.countDown();
               } catch (Exception e) {
                   sendDone.countDown();
                   e.printStackTrace();
                   fail(e.toString());
               }
            }
        });

        assertTrue("messages sending done", sendDone.await(180, TimeUnit.SECONDS));
        assertEquals("all message were sent", numMessages, sent.get());

        doAck.countDown();
        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);

        assertTrue("usage goes to 0 once consumer goes away", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return 0 == TestSupport.getDestination(broker,
                        ActiveMQDestination.transform(destination)).getMemoryUsage().getPercentUsage();
            }
        }));
    }

};