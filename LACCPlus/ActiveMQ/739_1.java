//,temp,JMSConsumerTest.java,533,547,temp,AMQ1893Test.java,138,153
//,3
public class xxx {
            @Override
            public void onMessage(Message m) {
                try {
                    TextMessage tm = (TextMessage)m;
                    LOG.info("Got in second listener: " + tm.getText());
                    // order is not guaranteed as the connection is started before the listener is set.
                    // assertEquals("" + counter.get(), tm.getText());
                    counter.incrementAndGet();
                    if (counter.get() == 4) {
                        done2.countDown();
                    }
                } catch (Throwable e) {
                    LOG.error("unexpected ex onMessage: ", e);
                }
            }

};