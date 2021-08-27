//,temp,FailoverDurableSubTransactionTest.java,258,269,temp,FailoverDurableSubTransactionTest.java,162,171
//,3
public class xxx {
                    @Override
                    public void postProcessDispatch(MessageDispatch messageDispatch) {
                        super.postProcessDispatch(messageDispatch);
                        if ((FailType.ON_DISPATCH.equals(failType) || FailType.ON_DISPACH_WITH_REPLAY_DELAY.equals(failType)) && dispatchedCount.incrementAndGet() == errorAt) {
                            for (TransportConnection transportConnection : broker.getTransportConnectors().get(0).getConnections()) {
                                LOG.error("Whacking connection on dispatch: " + transportConnection);
                                transportConnection.serviceException(new IOException("ERROR NOW"));
                            }
                        }
                    }

};