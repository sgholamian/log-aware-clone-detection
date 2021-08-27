//,temp,FailoverConsumerUnconsumedTest.java,246,263,temp,FailoverXATransactionTest.java,102,121
//,3
public class xxx {
                    @Override
                    public int prepareTransaction(final ConnectionContext context,
                                                  TransactionId xid) throws Exception {
                        int result = super.prepareTransaction(context, xid);
                        if (first.compareAndSet(false, true)) {
                            context.setDontSendReponse(true);
                            Executors.newSingleThreadExecutor().execute(new Runnable() {
                                public void run() {
                                    LOG.info("Stopping broker on prepare");
                                    try {
                                        context.getConnection().stop();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }

                        return result;
                    }

};