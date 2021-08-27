//,temp,JMSConsumerTest.java,583,599,temp,JMSConsumerTest.java,495,511
//,2
public class xxx {
            @Override
            public void onMessage(Message m) {
                try {
                    TextMessage tm = (TextMessage)m;
                    LOG.info("Got in first listener: " + tm.getText());
                    assertEquals("" + counter.get(), tm.getText());
                    counter.incrementAndGet();
                    if (counter.get() == 2) {
                        sendDone.await();
                        connection.close();
                        got2Done.countDown();
                    }
                    tm.acknowledge();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

};