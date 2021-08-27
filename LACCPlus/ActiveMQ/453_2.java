//,temp,ThreeBrokerTempDestDemandSubscriptionCleanupTest.java,150,206,temp,ThreeBrokerTempDestDemandSubscriptionCleanupTest.java,77,117
//,3
public class xxx {
            @Override
            public void run() {
                for (int i = 0; i < NUM_ITER; i++) {

                    Connection conn = null;
                    try {
                        conn = brokerA.createConnection();

                        conn.start();

                        final Session sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
                        Destination destination = sess.createQueue(ECHO_QUEUE_NAME);

                        MessageProducer producer = sess.createProducer(destination);

                        LOG.info("Starting iter: " + i);
                        Destination replyTo = sess.createTemporaryQueue();
                        MessageConsumer responseConsumer = sess.createConsumer(replyTo);

                        Message message = sess.createTextMessage("Iteration: " + i);
                        message.setJMSReplyTo(replyTo);

                        producer.send(message);

                        TextMessage response = (TextMessage)responseConsumer.receive(CONSUME_TIMEOUT);
                        assertNotNull("We should have gotten a response, but didn't for iter: " + i, response);
                        assertEquals("We got the wrong response from the echo service", "Iteration: " + i, response.getText());


                        // so we close the consumer so that an actual RemoveInfo command gets propogated through the
                        // network
                        responseConsumer.close();
                        conn.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                        fail();
                    }

                }
            }

};