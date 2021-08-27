//,temp,FailoverTransactionTest.java,508,529,temp,FailoverConsumerOutstandingCommitTest.java,206,227
//,3
public class xxx {
            @Override
            public void commitTransaction(ConnectionContext context,
                    TransactionId xid, boolean onePhase) throws Exception {
                // from the consumer perspective whether the commit completed on the broker or
                // not is irrelevant, the transaction is still in doubt in the absence of a reply
                if (doActualBrokerCommit) {
                    LOG.info("doing actual broker commit...");
                    super.commitTransaction(context, xid, onePhase);
                }
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