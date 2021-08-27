//,temp,JmsQueueBrowserExpirationTest.java,173,189,temp,MemoryUsageBlockResumeTest.java,125,140
//,3
public class xxx {
            @Override
            public void run() {
                try {
                    Session session = producerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    MessageProducer producer = session.createProducer(destination);
                    producer.setDeliveryMode(deliveryMode);
                    for (int idx = 0; idx < toSend; ++idx) {
                        Message message = session.createTextMessage(new String(buf) + idx);
                        producer.send(destination, message);
                        messagesSent.incrementAndGet();
                        LOG.info("After little:" + idx + ", System Memory Usage " + broker.getSystemUsage().getMemoryUsage().getPercentUsage());
                    }
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }

};