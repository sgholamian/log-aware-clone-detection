//,temp,AMQ2314Test.java,103,126,temp,DiscriminatingConsumerLoadTest.java,270,299
//,3
public class xxx {
            public void run() {
                try {
                    int count = 0;
                    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    MessageConsumer consumer = session.createConsumer(destination);

                    while (consumer.receive(messageReceiveTimeout) == null) {
                        consumerReady.countDown();
                    }
                    count++;
                    LOG.info("Received one... waiting");
                    consumerContinue.await();
                    if (consumeAll) {
                        LOG.info("Consuming the rest of the messages...");
                        while (consumer.receive(messageReceiveTimeout) != null) {
                            count++;
                        }
                    }
                    LOG.info("consumer session closing: consumed count: " + count);
                    session.close();
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }

};