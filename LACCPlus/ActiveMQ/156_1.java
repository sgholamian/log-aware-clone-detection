//,temp,FailoverConsumerOutstandingCommitTest.java,110,125,temp,FailoverXATransactionTest.java,162,179
//,3
public class xxx {
                    @Override
                    public void commitTransaction(ConnectionContext context,
                            TransactionId xid, boolean onePhase) throws Exception {
                        // so commit will hang as if reply is lost
                        context.setDontSendReponse(true);
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            public void run() {
                                LOG.info("Stopping broker before commit...");
                                try {
                                    broker.stop();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

};