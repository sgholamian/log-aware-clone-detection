//,temp,FailoverConsumerUnconsumedTest.java,112,129,temp,FailoverTransactionTest.java,881,895
//,3
public class xxx {
                    @Override
                    public void removeConsumer(ConnectionContext context, final ConsumerInfo info) throws Exception {
                        if (count++ == 1) {
                            Executors.newSingleThreadExecutor().execute(new Runnable() {
                                public void run() {
                                    LOG.info("Stopping broker on removeConsumer: " + info);
                                    try {
                                        broker.stop();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }

};