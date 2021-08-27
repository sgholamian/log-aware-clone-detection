//,temp,FailoverDurableSubTransactionTest.java,258,269,temp,FailoverDurableSubTransactionTest.java,162,171
//,3
public class xxx {
                    @Override
                    public void acknowledge(ConsumerBrokerExchange consumerExchange, MessageAck ack) throws Exception {
                        LOG.info("ack request: " + ack);
                        if (FailType.ON_ACK.equals(failType) /*&& ack.getAckType() == MessageAck.DELIVERED_ACK_TYPE*/ && dispatchedCount.incrementAndGet() == errorAt) {
                            for (TransportConnection transportConnection : broker.getTransportConnectors().get(0).getConnections()) {
                                LOG.error("Whacking connection on ack: " + transportConnection);
                                transportConnection.serviceException(new IOException("ERROR NOW"));
                            }
                        } else {
                            super.acknowledge(consumerExchange, ack);
                        }
                    }

};