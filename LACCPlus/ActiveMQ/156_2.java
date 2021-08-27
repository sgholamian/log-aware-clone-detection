//,temp,FailoverConsumerOutstandingCommitTest.java,110,125,temp,FailoverXATransactionTest.java,162,179
//,3
public class xxx {
                    @Override
                    public void commitTransaction(final ConnectionContext context,
                                                  TransactionId xid, boolean onePhase) throws Exception {
                        super.commitTransaction(context, xid, onePhase);
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
                    }

};