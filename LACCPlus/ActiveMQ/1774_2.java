//,temp,DuplicateFromStoreTest.java,279,347,temp,DuplicateFromStoreTest.java,191,260
//,3
public class xxx {
        public void run() {

            Connection connection = null;
            Session session = null;
            MessageProducer producer = null;

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
                Destination destination;
                if (isTopicDest) {
                    // Create the destination (Topic or Queue)
                    destination = session.createTopic(destName);
                } else {
                    destination = session.createQueue(destName);
                }
                // Create a MessageProducer from the Session to the Topic or Queue
                producer = session.createProducer(destination);

                // Create message
                long counter = 0;
                //enlarge msg to 16 kb
                int msgSize = 16 * 1024;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.setLength(msgSize + 15);
                stringBuilder.append("Message: ");
                stringBuilder.append(counter);
                for (int j = 0; j < (msgSize / 10); j++) {
                    stringBuilder.append("XXXXXXXXXX");
                }
                String text = stringBuilder.toString();
                TextMessage message = session.createTextMessage(text);

                // send message
                while (totalMessagesToSend.decrementAndGet() >= 0) {
                    producer.send(message);
                    totalMessagesSent.incrementAndGet();
                    log.debug("Sent message: " + counter);
                    counter++;

                    if ((counter % 10000) == 0)
                        log.info("sent " + counter + " messages");

                    Thread.sleep(PRODUCER_SLEEP);
                }
            } catch (Exception ex) {
                log.error(ex.toString());
                return;
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (Exception ignored) {
                } finally {
                    producersFinished.countDown();
                }
            }
            log.debug("Closing producer for " + destName);
        }

};