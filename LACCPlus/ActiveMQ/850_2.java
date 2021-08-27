//,temp,JmsTimeoutTest.java,58,79,temp,AMQ6240Test.java,50,72
//,3
public class xxx {
            public void run() {
                int count=0;
                try {
                    LOG.info("Sender thread starting");
                    Session session = cx.createSession(true, Session.SESSION_TRANSACTED);
                    MessageProducer producer = session.createProducer(queue);

                    BytesMessage message = session.createBytesMessage();
                    message.writeBytes(new byte[8*1024]);
                    for(; count<100; count++){
                        producer.send(message);
                    }
                    LOG.info("Done sending..");
                } catch (JMSException e) {
                    if (e.getCause() instanceof RequestTimedOutIOException) {
                        exceptionCount.incrementAndGet();
                        LOG.info("Got expected send time out on message: " + count);
                    } else {
                        e.printStackTrace();
                    }
                    return;
                }
            }

};