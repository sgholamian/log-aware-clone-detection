//,temp,FailoverTransactionTest.java,390,407,temp,JmsJdbcXATest.java,178,199
//,3
public class xxx {
                    @Override
                    public void commitTransaction(ConnectionContext context,
                                                  TransactionId xid, boolean onePhase) throws Exception {
                        if (onePhase) {
                            super.commitTransaction(context, xid, onePhase);
                        } else {
                            // die before doing the commit
                            // so commit will hang as if reply is lost
                            context.setDontSendReponse(true);
                            Executors.newSingleThreadExecutor().execute(new Runnable() {
                                @Override
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
                    }

};