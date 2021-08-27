//,temp,AMQ4485LowLimitTest.java,316,325,temp,AMQ4485NetworkOfXBrokersWithNDestsFanoutTransactionTest.java,205,214
//,2
public class xxx {
                @Override
                public void onMessage(Message message) {
                    try {
                        producer.send(message);
                        queueSession.commit();
                    } catch (Exception e) {
                        LOG.error("Failed to fanout to GW: " + message, e);
                    }

                }

};