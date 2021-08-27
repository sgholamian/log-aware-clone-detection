//,temp,SlowConsumerTest.java,69,83,temp,AmqpAndStompInteropTest.java,130,144
//,3
public class xxx {
            public void run() {
                try {
                    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    MessageProducer producer = session.createProducer(new ActiveMQQueue(getDestinationName()));
                    for (int idx = 0; idx < MESSAGES_COUNT; ++idx) {
                        Message message = session.createTextMessage("" + idx);
                        producer.send(message);
                        LOG.debug("Sending: " + idx);
                    }
                    producer.close();
                    session.close();
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }

};