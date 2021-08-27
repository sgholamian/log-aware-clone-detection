//,temp,MessageGroupNewConsumerTest.java,57,161,temp,MessageGroupCloseTest.java,53,166
//,3
public class xxx {
    public void testNewConsumer() throws JMSException, InterruptedException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(connStr);
        connection = factory.createConnection();
        connection.start();
        final String queueName = this.getClass().getSimpleName();
        final Thread producerThread = new Thread() {
            public void run() {
                try {
                    Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
                    Queue queue = session.createQueue(queueName);
                    MessageProducer prod = session.createProducer(queue);
                    for (int i=0; i<10; i++) {
                        for (int j=0; j<10; j++) {
                            int seq = j + 1;
                            if ((j+1) % 5 == 0) {
                                seq = -1;
                            }
                            Message message = generateMessage(session, Integer.toString(i), seq);
                            prod.send(message);
                            session.commit();
                            messagesSent++;
                            LOG.info("Sent message: group=" + i + ", seq="+ seq);
                            //Thread.sleep(20);
                        }
                        if (i % 100 == 0) {
                            LOG.info("Sent messages: group=" + i);
                        }
                        setMessageGroupCount(getMessageGroupCount() + 1);
                    }
                    LOG.info(messagesSent+" messages sent");
                    latchMessagesCreated.countDown();
                    prod.close();
                    session.close();
                } catch (Exception e) {
                    LOG.error("Producer failed", e);
                }
            }
        };
        final Thread consumerThread1 = new Thread() {
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
        final Thread consumerThread2 = new Thread() {
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
        consumerThread2.start();
        consumerThread1.start();
        producerThread.start();
        // wait for threads to finish
        producerThread.join();
        consumerThread1.join();
        consumerThread2.join();
        connection.close();
        // check results

        assertEquals("consumers should get all the messages", messagesSent, messagesRecvd1 + messagesRecvd2);
        assertEquals("not all message groups closed for consumer 1", messageGroups1.size(), closedGroups1.size());
        assertEquals("not all message groups closed for consumer 2", messageGroups2.size(), closedGroups2.size());
        assertTrue("producer failed to send any messages", messagesSent > 0);
        assertEquals("JMSXGroupFirstForConsumer not set", 0, errorCountFirstForConsumer);
        assertEquals("wrong consumer got close message", 0, errorCountWrongConsumerClose);
        assertEquals("consumer got duplicate close message", 0, errorCountDuplicateClose);
    }

};