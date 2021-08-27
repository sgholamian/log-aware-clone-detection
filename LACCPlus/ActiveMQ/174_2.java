//,temp,FailoverDurableSubTransactionTest.java,244,255,temp,FailoverDurableSubTransactionTest.java,139,149
//,3
public class xxx {
                    @Override
                    public void commitTransaction(ConnectionContext context, TransactionId xid, boolean onePhase) throws Exception {
                        if (FailType.ON_COMMIT.equals(failType) && dispatchedCount.incrementAndGet() == errorAt) {
                            for (TransportConnection transportConnection : broker.getTransportConnectors().get(0).getConnections()) {
                                LOG.error("Whacking connection on commit: " + transportConnection);
                                transportConnection.serviceException(new IOException("ERROR NOW"));
                            }
                        } else {
                            super.commitTransaction(context, xid, onePhase);
                        }
                    }

};