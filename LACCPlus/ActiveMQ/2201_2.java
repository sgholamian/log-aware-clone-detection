//,temp,FailoverPrefetchZeroTest.java,90,105,temp,FailoverTransactionTest.java,280,296
//,3
public class xxx {
                    @Override
                    public void commitTransaction(ConnectionContext context,
                                                  TransactionId xid, boolean onePhase) throws Exception {
                        super.commitTransaction(context, xid, onePhase);
                        // so commit will hang as if reply is lost
                        context.setDontSendReponse(true);
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            public void run() {
                                LOG.info("Stopping broker post commit...");
                                try {
                                    broker.stop();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

};