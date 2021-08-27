//,temp,FailoverTransactionTest.java,734,750,temp,FailoverTransactionTest.java,185,201
//,3
public class xxx {
                    @Override
                    public void acknowledge(
                            ConsumerBrokerExchange consumerExchange,
                            final MessageAck ack) throws Exception {

                        consumerExchange.getConnectionContext().setDontSendReponse(true);
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            public void run() {
                                LOG.info("Stopping broker on ack: " + ack);
                                try {
                                    broker.stop();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

};