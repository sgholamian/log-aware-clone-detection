//,temp,MessageGroupNewConsumerTest.java,94,121,temp,MessageGroupCloseTest.java,121,147
//,3
public class xxx {
            public void run() {
                try {
                    Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
                    Queue queue = session.createQueue(queueName);
                    MessageConsumer con1 = session.createConsumer(queue);
                    latchMessagesCreated.await();
                    while(true) {
                        Message message = con1.receive(1000);
                        if (message == null)  break;
                        LOG.info("Con1 got message "+formatMessage(message));
                        session.commit();
                        messagesRecvd1++;
                        // since we get the messages in order, the first few messages will be one from each group
                        // after we get one from each group, start the other consumer
                        if (messagesRecvd1 == groupNames.length) {
                            LOG.info("All groups acquired");
                            latchGroupsAcquired.countDown();
                            Thread.sleep(1000);
                        }
                        Thread.sleep(50);
                    }
                    LOG.info(messagesRecvd1+" messages received by consumer1");
                    con1.close();
                    session.close();
                } catch (Exception e) {
                    LOG.error("Consumer 1 failed", e);
                }
            }

};