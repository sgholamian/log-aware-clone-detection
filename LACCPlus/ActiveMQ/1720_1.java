//,temp,AMQ4475Test.java,296,342,temp,AMQ4475Test.java,227,275
//,3
public class xxx {
        @Override
        public void run() {
            Connection connection = null;
            try {

                for (int index = 0; index < PARALLEL; index++) {

                    ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(uri);

                    connection = factory.createConnection();
                    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    MessageConsumer consumer = session.createConsumer(dest);
                    connection.start();
                    int count = 0;

                    while (count < blockSize) {

                        Object msg1 = consumer.receive(10000);
                        if (msg1 != null) {
                            if (msg1 instanceof ActiveMQTextMessage) {
                                if (count % 100 == 0) {
                                    LOG.info("Consuming -> " + ((ActiveMQTextMessage) msg1).getDestination() + " count=" + count);
                                }

                                count++;
                            } else {
                                LOG.info("Skipping unknown msg type " + msg1);
                            }
                        } else {
                            failed = true;
                            break;
                        }
                    }

                    LOG.info("[" + dest.getQueueName() + "] completed segment (" + index + " of " + blockSize + ")");
                    connection.close();
                }
            } catch (Exception e) {
                LOG.warn("Caught unexpected exception: {}", e);
            } finally {
                LOG.debug(getName() + ": is stopping");
                try {
                    connection.close();
                } catch (Throwable e) {
                }
            }
        }

};