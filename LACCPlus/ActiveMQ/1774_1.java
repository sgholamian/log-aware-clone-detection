//,temp,DuplicateFromStoreTest.java,279,347,temp,DuplicateFromStoreTest.java,191,260
//,3
public class xxx {
        public void run() {

            Connection connection = null;
            Session session = null;
            MessageConsumer consumer = null;

            try {
                ActiveMQConnectionFactory amq = new ActiveMQConnectionFactory(activemqURL);
                connection = amq.createConnection();
                connection.setExceptionListener(new javax.jms.ExceptionListener() {
                    public void onException(javax.jms.JMSException e) {
                        e.printStackTrace();
                    }
                });
                connection.start();
                // Create a Session
                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                // Create the destination (Topic or Queue)
                Destination destination = null;
                if (isTopic)
                    destination = session.createTopic(queueName);
                else
                    destination = session.createQueue(queueName);

                //Create a MessageConsumer from the Session to the Topic or Queue
                consumer = session.createConsumer(destination);

                synchronized (init) {
                    init.notifyAll();
                }

                // Wait for a message
                long counter = 0;
                while (totalReceived.get() < NUM_MSGS) {
                    Message message2 = consumer.receive(5000);

                    if (message2 instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message2;
                        String text = textMessage.getText();
                        log.debug("Received: " + text.substring(0, 50));
                    } else if (totalReceived.get() < NUM_MSGS) {
                        log.error("Received message of unsupported type. Expecting TextMessage. count: " + totalReceived.get());
                    } else {
                        // all done
                        break;
                    }
                    if (message2 != null) {
                        counter++;
                        totalReceived.incrementAndGet();
                        if ((counter % 10000) == 0)
                            log.info("received " + counter + " messages");

                        Thread.sleep(CONSUMER_SLEEP);
                    }
                }
            } catch (Exception e) {
                log.error("Error in Consumer: " + e.getMessage());
                return;
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (Exception ignored) {
                } finally {
                    consumersFinished.countDown();
                }
            }
        }

};