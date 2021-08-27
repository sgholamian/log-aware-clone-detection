//,temp,MessageGroupNewConsumerTest.java,94,121,temp,MessageGroupCloseTest.java,121,147
//,3
public class xxx {
            public void run() {
                try {
                    latchMessagesCreated.await();
                    LOG.info("starting consumer2");
                    Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
                    Queue queue = session.createQueue(queueName);
                    MessageConsumer con2 = session.createConsumer(queue);
                    while(true) {
                        Message message = con2.receive(5000);
                        if (message == null) { break; }
                        LOG.info("Con2: got message "+formatMessage(message));
                        checkMessage(message, "Con2", messageGroups2, closedGroups2);
                        session.commit();
                        messagesRecvd2++;
                        if (messagesRecvd2 % 100 == 0) {
                            LOG.info("Con2: got messages count=" + messagesRecvd2);
                        }
                        //Thread.sleep(50);
                    }
                    con2.close();
                    session.close();
                    LOG.info("Con2: total messages=" + messagesRecvd2);
                    LOG.info("Con2: total message groups=" + messageGroups2.size());
                } catch (Exception e) {
                    LOG.error("Consumer 2 failed", e);
                }
            }

};