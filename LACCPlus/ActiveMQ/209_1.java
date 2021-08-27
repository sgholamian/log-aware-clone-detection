//,temp,JmsTimeoutTest.java,101,121,temp,MemoryUsageBlockResumeTest.java,105,120
//,3
public class xxx {
            public void run() {
                try {
                    LOG.info("Sender thread starting");
                    Session session = cx.createSession(false, 1);
                    MessageProducer producer = session.createProducer(queue);
                    producer.setDeliveryMode(DeliveryMode.PERSISTENT);

                    TextMessage message = session.createTextMessage(createMessageText());
                    for(int count=0; count<messageCount; count++){
                        producer.send(message);
                    }
                    LOG.info("Done sending..");
                } catch (JMSException e) {
                    if (e instanceof ResourceAllocationException || e.getCause() instanceof RequestTimedOutIOException) {
                        exceptionCount.incrementAndGet();
                    } else {
                        e.printStackTrace();
                    }
                    return;
                }
            }

};