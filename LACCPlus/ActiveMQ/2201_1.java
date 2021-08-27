//,temp,FailoverPrefetchZeroTest.java,90,105,temp,FailoverTransactionTest.java,280,296
//,3
public class xxx {
                    @Override
                    public Response messagePull(ConnectionContext context, final MessagePull pull) throws Exception {
                        context.setDontSendReponse(true);
                        pullDone.countDown();
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            public void run() {
                                LOG.info("Stopping broker on pull: " + pull);
                                try {
                                    broker.stop();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        return null;
                    }

};