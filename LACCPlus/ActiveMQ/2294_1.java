//,temp,MessageEvictionTest.java,160,189,temp,MessageEvictionTest.java,109,145
//,3
public class xxx {
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

};