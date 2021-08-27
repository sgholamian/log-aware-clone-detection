//,temp,PriorityRedeliveryOrderTest.java,146,207,temp,MessageGroupCloseTest.java,92,118
//,3
public class xxx {
            public void run() {
                try {
                    latchMessagesCreated.await();
                    LOG.info("starting consumer1");
                    Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
                    Queue queue = session.createQueue(queueName);
                    MessageConsumer con1 = session.createConsumer(queue);
                    while(true) {
                        Message message = con1.receive(5000);
                        if (message == null) break;
                        LOG.info("Con1: got message "+formatMessage(message));
                        checkMessage(message, "Con1", messageGroups1, closedGroups1);
                        session.commit();
                        messagesRecvd1++;
                        if (messagesRecvd1 % 100 == 0) {
                            LOG.info("Con1: got messages count=" + messagesRecvd1);
                        }
                        //Thread.sleep(50);
                    }
                    LOG.info("Con1: total messages=" + messagesRecvd1);
                    LOG.info("Con1: total message groups=" + messageGroups1.size());
                    con1.close();
                    session.close();
                } catch (Exception e) {
                    LOG.error("Consumer 1 failed", e);
                }
            }

};